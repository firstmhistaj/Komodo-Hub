package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SystemAdminActivity extends AppCompatActivity {

    private EditText editSchoolName, editAdminUsername, editAdminEmail, editAdminPassword, editAdminRole;
    private Button btnRegisterSchool, btnRegisterAdmin, btnBackToLogin;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_admin);

        // Initialize database and views
        db = new Database(this);
        editSchoolName = findViewById(R.id.editSchoolName);
        editAdminUsername = findViewById(R.id.editAdminUsername);
        editAdminEmail = findViewById(R.id.editAdminEmail);
        editAdminPassword = findViewById(R.id.editAdminPassword);
        editAdminRole = findViewById(R.id.editAdminRole);
        btnRegisterSchool = findViewById(R.id.btnRegisterSchool);
        btnRegisterAdmin = findViewById(R.id.btnRegisterAdmin);
        btnBackToLogin = findViewById(R.id.btnBackToLogin); // Back button

        // Set actions for each button
        btnRegisterSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSchool();
            }
        });

        btnRegisterAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAdmin();
            }
        });

        // Back button click listener to go back to the login screen
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

        if (schoolName.isEmpty()) {
            Toast.makeText(this, "Please enter a school name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.addSchool(schoolName)) {
            Toast.makeText(this, "School registered successfully", Toast.LENGTH_SHORT).show();
            editSchoolName.setText(""); // Clear the school name field
        } else {
            Toast.makeText(this, "School already exists or registration failed", Toast.LENGTH_SHORT).show();
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
            resetAdminForm();
        } else {
            Toast.makeText(this, "Failed to register Admin", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetAdminForm() {
        editAdminUsername.setText("");
        editAdminEmail.setText("");
        editAdminPassword.setText("");
        editAdminRole.setText(""); // Clear the role field
    }
}
