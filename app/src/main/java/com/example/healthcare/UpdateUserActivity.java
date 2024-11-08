package com.example.healthcare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateUserActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextNewEmail, editTextNewPassword, editTextNewRole;
    private Button buttonUpdateUser;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // Initialize database and UI elements
        db = new Database(this);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextNewEmail = findViewById(R.id.editTextNewEmail);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextNewRole = findViewById(R.id.editTextNewRole);
        buttonUpdateUser = findViewById(R.id.buttonUpdateUser);

        // Set onClickListener for Update button
        buttonUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String newEmail = editTextNewEmail.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String newRole = editTextNewRole.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(UpdateUserActivity.this, "Username is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call database update method
                boolean isUpdated = db.updateUser(username,
                        newEmail.isEmpty() ? null : newEmail,
                        newPassword.isEmpty() ? null : newPassword,
                        newRole.isEmpty() ? null : newRole);

                if (isUpdated) {
                    Toast.makeText(UpdateUserActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateUserActivity.this, "User update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
