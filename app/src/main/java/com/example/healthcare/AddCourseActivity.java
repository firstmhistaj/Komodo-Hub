package com.example.healthcare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCourseActivity extends AppCompatActivity {

    private Database database;
    private EditText editTextNewCourseName;
    private Button buttonAddCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        database = new Database(this);

        // Initialize UI elements
        editTextNewCourseName = findViewById(R.id.editTextNewCourseName);
        buttonAddCourse = findViewById(R.id.buttonAddCourse);

        // Set up "Add Course" button functionality
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = editTextNewCourseName.getText().toString().trim();

                // Check if the input field is empty
                if (courseName.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please enter a course name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Attempt to add the course to the database
                boolean success = database.addCourse(courseName);
                if (success) {
                    Toast.makeText(AddCourseActivity.this, "Course added successfully!", Toast.LENGTH_SHORT).show();
                    editTextNewCourseName.setText("");  // Clear the input field
                } else {
                    Toast.makeText(AddCourseActivity.this, "Course already exists or an error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
