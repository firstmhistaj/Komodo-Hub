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
    private Button buttonAddCourse;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        // Initialize the views and database
        editTextNewCourseName = findViewById(R.id.editTextNewCourseName);
        buttonAddCourse = findViewById(R.id.buttonAddCourse);
        db = new Database(this);

        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = editTextNewCourseName.getText().toString().trim();

                // Validate the course name input
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

    }
}


