package com.example.healthcare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCourseActivity extends AppCompatActivity {
    private EditText editTextNewCourseName;
    private EditText editTextUsername;
    private EditText editTextCourse;
    private Button buttonAddCourse;
    private Button buttonAssignCourse;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        // Initialize the views and database
        editTextNewCourseName = findViewById(R.id.editTextNewCourseName);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextCourse = findViewById(R.id.editTextCourse);
        buttonAddCourse = findViewById(R.id.buttonAddCourse);
        buttonAssignCourse = findViewById(R.id.buttonAssignCourse);
        db = new Database(this);

        // Add course functionality
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = editTextNewCourseName.getText().toString().trim();
                if (!courseName.isEmpty()) {
                    boolean isAdded = db.addCourse(courseName);
                    if (isAdded) {
                        Toast.makeText(AddCourseActivity.this, "Course added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddCourseActivity.this, "Course already exists or failed to add", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddCourseActivity.this, "Please enter a course name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Assign course to user functionality
        buttonAssignCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String courseName = editTextCourse.getText().toString().trim();

                if (!username.isEmpty() && !courseName.isEmpty()) {
                    boolean isAssigned = db.assignCourse(username, courseName);
                    if (isAssigned) {
                        Toast.makeText(AddCourseActivity.this, "Course assigned to user successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddCourseActivity.this, "Failed to assign course; check if course or user exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddCourseActivity.this, "Please enter both username and course name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
