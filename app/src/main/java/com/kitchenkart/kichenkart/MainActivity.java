package com.kitchenkart.kichenkart;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize components
        EditText mobileNumber = findViewById(R.id.mobileNumber);
        MaterialButton googleButton = findViewById(R.id.googleButton);
        MaterialButton emailButton = findViewById(R.id.emailButton);
        TextView findAccount = findViewById(R.id.findAccount);

        // Set click listeners
        googleButton.setOnClickListener(v -> signInWithGoogle());
        emailButton.setOnClickListener(v -> signInWithEmail());
        findAccount.setOnClickListener(v -> findAccount());
    }

    private void signInWithGoogle() {
        // Google sign-in implementation
    }

    private void signInWithEmail() {
        // Email sign-in implementation
    }

    private void findAccount() {
        // Account recovery implementation
    }
}