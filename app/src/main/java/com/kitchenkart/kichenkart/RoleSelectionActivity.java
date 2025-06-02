package com.kitchenkart.kichenkart;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class RoleSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        MaterialCardView sellerCard = findViewById(R.id.sellerCard);
        MaterialCardView customerCard = findViewById(R.id.customerCard);

        sellerCard.setOnClickListener(v -> navigateToRegistration("seller"));

        customerCard.setOnClickListener(v -> navigateToRegistration("customer"));
    }

    private void navigateToRegistration(String userType) {
        Intent intent = new Intent(RoleSelectionActivity.this, RegistrationActivity.class);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }
}