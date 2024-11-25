package com.example.komodo_hub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healthcare.R;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        // Handle window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the back button (ImageButton) by its ID
        ImageButton backButton = findViewById(R.id.buttonBack);

        // Set a click listener for the back button to navigate to LoginActivity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate back to LoginActivity
                Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);

                // Optionally, clear the activity stack to prevent navigating back to AdminDashboardActivity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                // Start the LoginActivity
                startActivity(intent);

                // Finish the current AdminDashboardActivity to remove it from the back stack
                finish();
            }
        });

        // Handle buttons for User Management
        Button buttonAddUser = findViewById(R.id.buttonAddUser);
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboardActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button buttonEditUser = findViewById(R.id.buttonEditUser);
        buttonEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboardActivity.this, UpdateUserActivity.class);
                startActivity(intent);
            }
        });

        Button buttonDeleteUser = findViewById(R.id.buttonDeleteUser);
        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, DeleteUserActivity.class);
                startActivity(intent);
            }
        });

        // Handle buttons for Course Management
        Button buttonAddCourse = findViewById(R.id.buttonAddCourse);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboardActivity.this, AddCourseActivity.class);
                startActivity(intent);
            }
        });

        Button buttonUpdateCourse = findViewById(R.id.buttonUpdateCourse);
        buttonUpdateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, EditCourseActivity.class);
                startActivity(intent);
            }
        });

        // Handle the View System Stats button
        Button buttonViewStats = findViewById(R.id.admin_view_stats_btn);
        buttonViewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, StatsActivity.class);
                startActivity(intent);
            }
        });
    }
}
