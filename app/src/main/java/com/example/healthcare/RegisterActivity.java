package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private EditText edFirstName, edUsername, edEmail, edPassword, edConfirm;
    private Button btnRegister;
    private TextView tvExistingUser;
    private Spinner userRoleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        edUsername = findViewById(R.id.editTextRegUsername);
        edEmail = findViewById(R.id.editTextRegEmail);
        edPassword = findViewById(R.id.editTextRegPassword);
        edConfirm = findViewById(R.id.editTextRegConfirmPassword);
        btnRegister = findViewById(R.id.buttonRegister);
        tvExistingUser = findViewById(R.id.textViewExistingUser);
        userRoleSpinner = findViewById(R.id.userRoleSpinner);

        // Set up the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userRoleSpinner.setAdapter(adapter);

        // Handle existing user click
        tvExistingUser.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        // Handle registration button click
        btnRegister.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String username = edUsername.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString();
        String confirmPassword = edConfirm.getText().toString();
        String selectedRole = userRoleSpinner.getSelectedItem().toString(); // Get selected role

        Database db = new Database(getApplicationContext());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Password and Confirm password didn't match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters, including letters, digits, and special symbols", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register user with role
        if (db.register(username, email, password, selectedRole)) {
            Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish(); // Close RegisterActivity
        } else {
            Toast.makeText(getApplicationContext(), "Registration failed, user may already exist", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPassword(String password) {
        boolean hasLetter = false, hasDigit = false, hasSpecialChar = false;

        if (password.length() < 8) {
            return false;
        }

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (isSpecialCharacter(c)) hasSpecialChar = true;
        }

        return hasLetter && hasDigit && hasSpecialChar;
    }

    private boolean isSpecialCharacter(char c) {
        // Check for special characters (ASCII values 33 to 46 and 64)
        return (c >= 33 && c <= 46) || c == 64;
    }
}
