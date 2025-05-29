package com.example.hackvortex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView gifImageView = findViewById(R.id.gifImageView);
        Glide.with(this).asGif().load(R.drawable.bg3).into(gifImageView);

        ImageButton button1 = findViewById(R.id.button1);
        ImageButton button2 = findViewById(R.id.button2);

        button1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Predictor.class);
            startActivity(intent);
        });

        button2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Analyzer.class);
            startActivity(intent);
        });
    }
}
