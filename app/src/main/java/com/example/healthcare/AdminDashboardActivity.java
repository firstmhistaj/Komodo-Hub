package com.example.healthcare;

import static com.example.healthcare.R.id.buttonAddCourse;
import static com.example.healthcare.R.id.buttonAddUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminDashboardActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonAddUser = findViewById(R.id.buttonAddUser);

        // Set an OnClickListener for the button
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where you handle what happens when the button is clicked
                // For example, to start a new activity:
                Intent intent = new Intent(AdminDashboardActivity.this, RegisterActivity.class);  // Replace ViewCourseActivity with the activity you want to navigate to
                startActivity(intent);
            }

        });


        Button buttonEditUser = findViewById(R.id.buttonEditUser);

        // Set click listener for Update User button
        buttonEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboardActivity.this, UpdateUserActivity.class);
                startActivity(intent);
            }
        });


        Button buttonAddCourse = findViewById(R.id.buttonAddCourse);
        // Set an OnClickListener for the button
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where you handle what happens when the button is clicked
                // For example, to start a new activity:
                Intent intent = new Intent(AdminDashboardActivity.this, AddCourseActivity.class);  // Replace ViewCourseActivity with the activity you want to navigate to
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

        // Add the OnClickListener for "View System Stats" button
        Button buttonViewStats = findViewById(R.id.admin_view_stats_btn);
        buttonViewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open StatsActivity to show the system stats
                Intent intent = new Intent(AdminDashboardActivity.this, StatsActivity.class);
                startActivity(intent);
            }
        });

        // Add the OnClickListener for "View System Stats" button
        Button buttonDeleteUser = findViewById(R.id.buttonDeleteUser);
        buttonDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open StatsActivity to show the system stats
                Intent intent = new Intent(AdminDashboardActivity.this, DeleteUserActivity.class);
                startActivity(intent);
            }
        });



    }




}