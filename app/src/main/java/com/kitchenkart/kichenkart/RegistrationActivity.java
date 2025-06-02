package com.kitchenkart.kichenkart;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private static final int FILE_PICKER_REQUEST_CODE = 100;
    private Uri pdfUri = null;  // Store selected PDF URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        String userType = getIntent().getStringExtra("userType");
        LinearLayout container = findViewById(R.id.registrationContainer);

        if (userType != null) {
            if (userType.equals("seller")) {
                setupSellerRegistration(container);
            } else {
                setupCustomerRegistration(container);
            }
        }
    }

    private void setupSellerRegistration(LinearLayout container) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View sellerView = inflater.inflate(R.layout.layout_seller_registration, container, false);
        container.addView(sellerView);

        Button submitButton = sellerView.findViewById(R.id.sellerSubmitButton);
        Button uploadButton = sellerView.findViewById(R.id.uploadPhiButton);

        submitButton.setOnClickListener(v -> registerSeller());
        uploadButton.setOnClickListener(v -> openFilePicker());
    }

    private void setupCustomerRegistration(LinearLayout container) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View customerView = inflater.inflate(R.layout.layout_customer_registration, container, false);
        container.addView(customerView);

        Button submitButton = customerView.findViewById(R.id.customerSubmitButton);
        Button birthdayButton = customerView.findViewById(R.id.birthdayButton);

        submitButton.setOnClickListener(v -> registerCustomer());
        birthdayButton.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    Button birthdayButton = findViewById(R.id.birthdayButton);
                    if (birthdayButton != null) {
                        birthdayButton.setText(date);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            Toast.makeText(this, "PDF selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerSeller() {
        // Get input values
        TextInputEditText firstName = findViewById(R.id.sellerFirstName);
        TextInputEditText lastName = findViewById(R.id.sellerLastName);
        TextInputEditText nic = findViewById(R.id.sellerNic);
        TextInputEditText postalCode = findViewById(R.id.sellerPostalCode);
        TextInputEditText password = findViewById(R.id.sellerPassword);
        TextInputEditText confirmPassword = findViewById(R.id.sellerConfirmPassword);
        RadioGroup genderGroup = findViewById(R.id.sellerGenderGroup);

        // Validate inputs
        if (validateSellerInputs(firstName, lastName, nic, postalCode, password, confirmPassword, genderGroup)) {
            // Registration logic
            Toast.makeText(this, "Seller registration successful", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerCustomer() {
        // Get input values
        TextInputEditText firstName = findViewById(R.id.customerFirstName);
        TextInputEditText lastName = findViewById(R.id.customerLastName);
        TextInputEditText password = findViewById(R.id.customerPassword);
        TextInputEditText confirmPassword = findViewById(R.id.customerConfirmPassword);
        RadioGroup genderGroup = findViewById(R.id.customerGenderGroup);
        Button birthdayButton = findViewById(R.id.birthdayButton);

        // Validate inputs
        if (validateCustomerInputs(firstName, lastName, password, confirmPassword, genderGroup, birthdayButton)) {
            // Registration logic
            Toast.makeText(this, "Customer registration successful", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateSellerInputs(
            TextInputEditText firstName,
            TextInputEditText lastName,
            TextInputEditText nic,
            TextInputEditText postalCode,
            TextInputEditText password,
            TextInputEditText confirmPassword,
            RadioGroup genderGroup) {

        boolean isValid = !isEditTextEmpty(firstName);

        // Validate text fields
        if (isEditTextEmpty(lastName)) isValid = false;
        if (isEditTextEmpty(nic)) isValid = false;
        if (isEditTextEmpty(postalCode)) isValid = false;
        if (isEditTextEmpty(password)) isValid = false;
        if (isEditTextEmpty(confirmPassword)) isValid = false;

        // Validate gender selection
        if (genderGroup == null || genderGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Validate PDF upload
        if (pdfUri == null) {
            Toast.makeText(this, "Please upload PHI Report PDF", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Validate password match
        if (password != null && confirmPassword != null) {
            String pass = Objects.requireNonNull(password.getText()).toString();
            String confirmPass = Objects.requireNonNull(confirmPassword.getText()).toString();
            if (!pass.equals(confirmPass)) {
                confirmPassword.setError("Passwords do not match");
                isValid = false;
            }
        }

        return isValid;
    }

    private boolean validateCustomerInputs(
            TextInputEditText firstName,
            TextInputEditText lastName,
            TextInputEditText password,
            TextInputEditText confirmPassword,
            RadioGroup genderGroup,
            Button birthdayButton) {

        boolean isValid = !isEditTextEmpty(firstName);

        // Validate text fields
        if (isEditTextEmpty(lastName)) isValid = false;
        if (isEditTextEmpty(password)) isValid = false;
        if (isEditTextEmpty(confirmPassword)) isValid = false;

        // Validate gender selection
        if (genderGroup == null || genderGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Validate birthday
        if (birthdayButton == null || birthdayButton.getText().toString().equals("Select Date")) {
            Toast.makeText(this, "Please select birthday", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Validate password match
        if (password != null && confirmPassword != null) {
            String pass = Objects.requireNonNull(password.getText()).toString();
            String confirmPass = Objects.requireNonNull(confirmPassword.getText()).toString();
            if (!pass.equals(confirmPass)) {
                confirmPassword.setError("Passwords do not match");
                isValid = false;
            }
        }

        return isValid;
    }

    private boolean isEditTextEmpty(TextInputEditText editText) {
        if (editText == null || editText.getText() == null || editText.getText().toString().trim().isEmpty()) {
            if (editText != null) {
                editText.setError("This field is required");
            }
            return true;
        }
        return false;
    }
}