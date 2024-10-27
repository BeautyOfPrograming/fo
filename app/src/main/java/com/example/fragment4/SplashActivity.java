package com.example.fragment4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView splashImage;
    // Optional: Text view and button references

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // Reference views
        splashImage = findViewById(R.id.splash_image);
        // Optional: Text view and button references

        // Load image from resources (replace with your image resource)
        splashImage.setImageResource(R.drawable.img_1);

        // Optional: Handle button clicks and navigation
        // ...

        // Simulate splash delay (optional)
        new Handler().postDelayed(() -> {
            // Start main activity or navigate to next screen
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000); // 2 seconds delay
    }
}