package com.example.hackvortex;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import android.view.inputmethod.InputMethodManager;


public class Predictor extends AppCompatActivity {
    private EditText editText1, editText2, editText3, editText4;
    private TextView responseText;
    private static final String API_KEY = "AIzaSyDcaZDYRR4LO9mL5kLnJlHYWW3BvWFQq10";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictor);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        Button sendButton = findViewById(R.id.sendButton);
        responseText = findViewById(R.id.responseText);

        sendButton.setOnClickListener(v -> sendPromptToGemini());
    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    private void sendPromptToGemini() {
        hideKeyboard();
        String cityState = editText1.getText().toString().trim();
        String vehicleType = editText2.getText().toString().trim();
        String axleConfiguration = editText3.getText().toString().trim();
        String primaryUsage = editText4.getText().toString().trim();

        // Validate inputs
        if (cityState.isEmpty() || vehicleType.isEmpty() || axleConfiguration.isEmpty() || primaryUsage.isEmpty()) {
            responseText.setText("Please fill all fields.");
            return;
        }

        // Hidden Prompt
        String prompt = "Based on the climate and road conditions of '" + cityState + "', " +
                "vehicle type '" + vehicleType + "', axle configuration '" + axleConfiguration + "', " +
                "and primary usage '" + primaryUsage + "', provide the best suitable tyre wear type " +
                "and exactly three tyre recommendations. Format the response strictly as follows:\n\n" +
                "Tyre-wear : <one-word-tyre-wear>\n" +
                "Recommendations:\n\n" +
                "1. <Brand1> <VersionName1>\n" +
                "2. <Brand2> <VersionName2>\n" +
                "3. <Brand3> <VersionName3>\n\n" +
                "Do not add any extra text or explanation.";


        OkHttpClient client = new OkHttpClient();

        JSONObject jsonRequest = new JSONObject();
        try {
            JSONArray partsArray = new JSONArray();
            partsArray.put(new JSONObject().put("text", prompt));

            JSONObject content = new JSONObject();
            content.put("parts", partsArray);

            JSONArray contentsArray = new JSONArray();
            contentsArray.put(content);

            jsonRequest.put("contents", contentsArray);
        } catch (Exception e) {
            runOnUiThread(() -> responseText.setText("JSON Error: " + e.getMessage()));
            return;
        }

        RequestBody body = RequestBody.create(jsonRequest.toString(), MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL + "?key=" + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> responseText.setText("Request Failed: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);

                        JSONArray candidates = jsonResponse.optJSONArray("candidates");
                        if (candidates != null && candidates.length() > 0) {
                            JSONObject firstCandidate = candidates.getJSONObject(0);
                            JSONObject content = firstCandidate.getJSONObject("content");
                            JSONArray parts = content.getJSONArray("parts");
                            String geminiResponse = parts.getJSONObject(0).getString("text");

                            runOnUiThread(() -> responseText.setText(geminiResponse));
                        } else {
                            runOnUiThread(() -> responseText.setText("No response received from Gemini."));
                        }
                    } catch (Exception e) {
                        runOnUiThread(() -> responseText.setText("Response Parsing Error: " + e.getMessage()));
                    }
                } else {
                    runOnUiThread(() -> responseText.setText("API Error: " + response.code() + " - " + response.message()));
                }
            }
        });
    }
}
