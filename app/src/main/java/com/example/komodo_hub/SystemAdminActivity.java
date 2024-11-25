package com.example.komodo_hub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;

public class SystemAdminActivity extends AppCompatActivity {

    private EditText editSchoolName, editAddressLine1, editCity, editState, editCountry, editPostalCode;
    private EditText editAdminUsername, editAdminFirstName, editAdminLastName, editAdminEmail, editAdminPassword, editAdminRole;
    private Button btnRegisterSchool, btnUpdateSchool, btnDeleteSchool, btnRegisterAdmin, btnBackToLogin;
    private Database db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_admin);

        // Initialize database and views
        db = new Database(this);
        editSchoolName = findViewById(R.id.editSchoolName);
        editAddressLine1 = findViewById(R.id.editAddressLine1);
        editCity = findViewById(R.id.editCity);
        editState = findViewById(R.id.editState);
        editCountry = findViewById(R.id.editCountry);
        editPostalCode = findViewById(R.id.editPostalCode);

        editAdminUsername = findViewById(R.id.editAdminUsername);
//        editAdminFirstName = findViewById(R.id.editAdminFirstName);
//        editAdminLastName = findViewById(R.id.editAdminLastName);
        editAdminEmail = findViewById(R.id.editAdminEmail);
        editAdminPassword = findViewById(R.id.editAdminPassword);
        editAdminRole = findViewById(R.id.editAdminRole);

        btnRegisterSchool = findViewById(R.id.btnRegisterSchool);
        btnUpdateSchool = findViewById(R.id.btnUpdateSchool);
        btnDeleteSchool = findViewById(R.id.btnDeleteSchool);
        btnRegisterAdmin = findViewById(R.id.btnRegisterAdmin);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);

        // Set actions for each button
        btnRegisterSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSchool();
            }
        });

        btnUpdateSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSchool();
            }
        });

        btnDeleteSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSchool();
            }
        });

        btnRegisterAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAdmin();
            }
        });

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SystemAdminActivity.this, LoginActivity.class));
                finish(); // Close the current activity
            }
        });
    }

    private void registerSchool() {
        String schoolName = editSchoolName.getText().toString().trim();
        String addressLine1 = editAddressLine1.getText().toString().trim();
        String city = editCity.getText().toString().trim();
        String state = editState.getText().toString().trim();
        String country = editCountry.getText().toString().trim();
        String postalCode = editPostalCode.getText().toString().trim();

        if (schoolName.isEmpty() || addressLine1.isEmpty() || city.isEmpty() ||
                state.isEmpty() || country.isEmpty() || postalCode.isEmpty()) {
            Toast.makeText(this, "Please fill in all school details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the school details to the database
        if (db.addSchool(schoolName, addressLine1, city, state, country, postalCode)) {
            Toast.makeText(this, "School registered successfully", Toast.LENGTH_SHORT).show();
            clearSchoolFields(); // Clear the fields after successful registration
        } else {
            Toast.makeText(this, "School already exists or registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSchool() {
        String schoolName = editSchoolName.getText().toString().trim();
        String addressLine1 = editAddressLine1.getText().toString().trim();
        String city = editCity.getText().toString().trim();
        String state = editState.getText().toString().trim();
        String country = editCountry.getText().toString().trim();
        String postalCode = editPostalCode.getText().toString().trim();

        if (schoolName.isEmpty() || addressLine1.isEmpty() || city.isEmpty() ||
                state.isEmpty() || country.isEmpty() || postalCode.isEmpty()) {
            Toast.makeText(this, "Please fill in all school details", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.updateSchool(schoolName, addressLine1, city, state, country, postalCode)) {
            Toast.makeText(this, "School updated successfully", Toast.LENGTH_SHORT).show();
            clearSchoolFields();
        } else {
            Toast.makeText(this, "Failed to update school. It may not exist.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSchool() {
        String schoolName = editSchoolName.getText().toString().trim();

        if (schoolName.isEmpty()) {
            Toast.makeText(this, "Please enter the school name to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.deleteSchool(schoolName)) {
            Toast.makeText(this, "School deleted successfully", Toast.LENGTH_SHORT).show();
            clearSchoolFields();
        } else {
            Toast.makeText(this, "Failed to delete school. It may not exist.", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerAdmin() {
        String adminUsername = editAdminUsername.getText().toString().trim();
        String adminEmail = editAdminEmail.getText().toString().trim();
        String adminPassword = editAdminPassword.getText().toString().trim();
        String adminRole = editAdminRole.getText().toString().trim();



        if (adminUsername.isEmpty() || adminEmail.isEmpty() || adminPassword.isEmpty() || adminRole.isEmpty()) {
            Toast.makeText(this, "Please fill in all admin details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register the admin with the specified role
        boolean isAdminRegistered = db.register(adminUsername, adminEmail, adminPassword, adminRole);

        if (isAdminRegistered) {
            Toast.makeText(this, "Admin registered successfully", Toast.LENGTH_SHORT).show();
            clearAdminFields(); // Clear the admin fields after successful registration
        } else {
            Toast.makeText(this, "Failed to register Admin", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearSchoolFields() {
        editSchoolName.setText("");
        editAddressLine1.setText("");
        editCity.setText("");
        editState.setText("");
        editCountry.setText("");
        editPostalCode.setText(""); // Clear the school-related fields
    }

    private void clearAdminFields() {
        editAdminUsername.setText("");
        editAdminEmail.setText("");
        editAdminPassword.setText("");
        editAdminRole.setText(""); // Clear the admin-related fields
    }
}
