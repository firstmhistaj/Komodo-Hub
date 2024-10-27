package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        edUsername = findViewById(R.id.editTextLoginUsername);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.buttonLogin);
        tv = findViewById(R.id.textViewNewUser);

        // Login button click listener
        btn.setOnClickListener(view -> {
            String username = edUsername.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            Database db = new Database(getApplicationContext());

            // Check if username or password is empty
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if the login is successful
            if (db.login(username, password)) {
                // Fetch user role from the database
                String userRole = db.getUserRole(username);

                Intent intent;
                switch (userRole) {
                    case "admin":
                        intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                        Toast.makeText(getApplicationContext(), "Logged in as Admin", Toast.LENGTH_SHORT).show();
                        break;

                    case "Student":
                        intent = new Intent(LoginActivity.this, StudentActivity.class);
                        Toast.makeText(getApplicationContext(), "Logged in as Student", Toast.LENGTH_SHORT).show();
                        break;

                    case "Teacher":
                        intent = new Intent(LoginActivity.this, TeacherActivity.class);
                        Toast.makeText(getApplicationContext(), "Logged in as Teacher", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(getApplicationContext(), "Unknown role", Toast.LENGTH_SHORT).show();
                        return;
                }

                // Save username in SharedPreferences
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("username", username);
                editor.apply();

                // Start the appropriate activity and close the login screen
                startActivity(intent);
                finish(); // Close login activity
            } else {
                Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        });

        // New user click listener
        tv.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }
}
