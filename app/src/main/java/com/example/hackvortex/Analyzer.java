package com.example.hackvortex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Analyzer extends AppCompatActivity {
    private static final String API_KEY = "AIzaSyDcaZDYRR4LO9mL5kLnJlHYWW3BvWFQq10";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    private EditText cityStateInput;
    private LinearLayout imagePreviewLayout;
    private Button selectImagesButton, analyzeButton;
    private TextView resultTextView;

    private List<Uri> selectedImages = new ArrayList<>();
    private static final int MAX_IMAGES = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzer);

        cityStateInput = findViewById(R.id.editText1);
        imagePreviewLayout = findViewById(R.id.imagePreviewLayout);
        selectImagesButton = findViewById(R.id.selectImagesButton);
        analyzeButton = findViewById(R.id.analyzeButton);
        resultTextView = findViewById(R.id.resultTextView);

        selectImagesButton.setOnClickListener(v -> pickImages());
        analyzeButton.setOnClickListener(v -> analyzeTyreImages());
    }

    private void pickImages() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imagePickerLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (result.getData().getClipData() != null) {
                        int count = result.getData().getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            if (selectedImages.size() < MAX_IMAGES) {
                                Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                                selectedImages.add(imageUri);
                                displaySelectedImage(imageUri);
                            }
                        }
                    } else if (result.getData().getData() != null) {
                        if (selectedImages.size() < MAX_IMAGES) {
                            Uri imageUri = result.getData().getData();
                            selectedImages.add(imageUri);
                            displaySelectedImage(imageUri);
                        }
                    }
                }
            });

    private void displaySelectedImage(Uri imageUri) {
        ImageView imageView = new ImageView(this);
        imageView.setImageURI(imageUri);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(400, 400));
        imageView.setPadding(10, 10, 10, 10);
        imagePreviewLayout.addView(imageView);
    }

    private void analyzeTyreImages() {
        if (selectedImages.isEmpty()) {
            Toast.makeText(this, "Please upload at least one image.", Toast.LENGTH_SHORT).show();
            return;
        }

        String cityState = cityStateInput.getText().toString().trim();
        if (cityState.isEmpty()) {
            Toast.makeText(this, "Please enter city and state.", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                JsonArray partsArray = new JsonArray();

                // Adding prompt as text part
                JsonObject textPart = new JsonObject();
                textPart.addProperty("text", "Analyze these tyre images considering the climate, road conditions, and temperature of " + cityState + ". Identify the Tyre Tread Pattern, Tread Depth, Cracks & Cuts, Bulges or Blisters, Punctures or Cuts, and Foreign Objects (Nails, glass shards, etc.). Predict tyre health, lifespan, and the next maintenance required for the tyre. Provide the output strictly in the following format: \n" +
                        "Tyre Health : (percentage%)\n\n" +
                        "Lifespan : (time duration)\n\n" +
                        "Next Maintenance Required : (time duration)");
                partsArray.add(textPart);

                // Adding image parts
                for (Uri imageUri : selectedImages) {
                    String base64Image = convertImageToBase64(imageUri);
                    if (base64Image != null) {
                        JsonObject imagePart = new JsonObject();
                        JsonObject inlineData = new JsonObject();
                        inlineData.addProperty("mime_type", "image/jpeg");
                        inlineData.addProperty("data", base64Image);

                        imagePart.add("inline_data", inlineData);
                        partsArray.add(imagePart);
                    }
                }

                // Wrapping content inside role 'user'
                JsonObject userContent = new JsonObject();
                userContent.addProperty("role", "user");
                userContent.add("parts", partsArray);

                // Constructing request body
                JsonObject requestBodyJson = new JsonObject();
                JsonArray contentsArray = new JsonArray();
                contentsArray.add(userContent);
                requestBodyJson.add("contents", contentsArray);

                RequestBody requestBody = RequestBody.create(requestBodyJson.toString(), MediaType.get("application/json"));
                Request request = new Request.Builder()
                        .url(API_URL + "?key=" + API_KEY)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();

                runOnUiThread(() -> {
                    try {
                        JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

                        // Log full API response for debugging
                        resultTextView.setText("Raw Response:\n" + responseBody);

                        if (jsonResponse.has("candidates")) {
                            JsonArray candidates = jsonResponse.getAsJsonArray("candidates");
                            if (candidates.size() > 0) {
                                JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
                                if (firstCandidate.has("content")) {
                                    JsonObject content = firstCandidate.getAsJsonObject("content");
                                    if (content.has("parts")) {
                                        JsonArray parts = content.getAsJsonArray("parts");
                                        if (parts.size() > 0) {
                                            JsonObject firstPart = parts.get(0).getAsJsonObject();
                                            if (firstPart.has("text")) {
                                                String resultText = firstPart.get("text").getAsString().trim();

                                                // Extract tyre health, lifespan, and next maintenance using regex
                                                String tyreHealth = extractValue(resultText, "tyre health");
                                                String lifespan = extractValue(resultText, "lifespan");
                                                String maintenance = extractValue(resultText, "next maintenance required");

                                                // Format the output
                                                String formattedOutput = "1. " + (tyreHealth.isEmpty() ? "N/A" : tyreHealth) + "\n" +
                                                        "2. " + (lifespan.isEmpty() ? "N/A" : lifespan) + "\n" +
                                                        "3. " + (maintenance.isEmpty() ? "N/A" : maintenance);

                                                resultTextView.setText(formattedOutput);
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // If no valid response, show raw response for debugging
                        resultTextView.setText("Unexpected API response:\n" + responseBody);
                    } catch (Exception e) {
                        resultTextView.setText("Error parsing response:\n" + responseBody);
                        e.printStackTrace();
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> resultTextView.setText("Error analyzing tyre images."));
            }
        }).start();
    }
    private String extractValue(String text, String key) {
        int index = text.toLowerCase().indexOf(key.toLowerCase());
        if (index != -1) {
            String[] lines = text.substring(index).split("\n");
            if (lines.length > 0) {
                return lines[0].replace(key + ":", "").trim();
            }
        }
        return "";
    }

    private String convertImageToBase64(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.NO_WRAP);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
