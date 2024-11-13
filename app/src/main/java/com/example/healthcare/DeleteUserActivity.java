package com.example.healthcare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteUserActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextRole;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        // Initialize views and database
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextRole = findViewById(R.id.editTextRole);
        db = new Database(this);

        Button buttonDeleteUser = findViewById(R.id.buttonDeleteUser);
        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username input from user
                String username = editTextUsername.getText().toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(DeleteUserActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Attempt to delete the user
                boolean isDeleted = db.deleteUser(username);

                // Show a message based on success or failure
                if (isDeleted) {
                    Toast.makeText(DeleteUserActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DeleteUserActivity.this, "User not found or deletion failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        db.close(); // Close the database when the activity is destroyed
        super.onDestroy();
    }
}
