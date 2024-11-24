package com.example.komodo_hub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;

import java.util.List;

public class AddCourseActivity extends AppCompatActivity {
    private EditText editTextNewCourseName;
    private EditText editTextUsername;
    private EditText editTextCourse;
    private Button buttonAddCourse;
    private Button buttonAssignCourse;
    private TextView textViewCourses;  // TextView to display courses
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
        textViewCourses = findViewById(R.id.textViewCourses);  // Find the TextView to display courses
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
                        displayCourses();  // Refresh course list after adding
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

        // Display the list of courses when activity is created
        displayCourses();
    }

    // Method to display the list of courses in the TextView
    private void displayCourses() {
        List<String> courses = db.getAllCourses();  // Get all courses from the database

        if (courses.isEmpty()) {
            textViewCourses.setText("No courses available.");
        } else {
            StringBuilder coursesList = new StringBuilder("Available Courses:\n");
            for (String course : courses) {
                coursesList.append(course).append("\n");
            }
            textViewCourses.setText(coursesList.toString());  // Set the courses to the TextView
        }
    }


    // Method to display the list of courses assigned to the user
//    private void displayUserCourses(String username) {
//        List<String> userCourses = db.getUserCourses(username);  // Get courses assigned to the user
//
//        if (userCourses.isEmpty()) {
//            textViewUserCourses.setText(username + " has no assigned courses.");
//        } else {
//            StringBuilder userCoursesList = new StringBuilder(username + "'s Assigned Courses:\n");
//            for (String course : userCourses) {
//                userCoursesList.append(course).append("\n");
//            }
//            textViewUserCourses.setText(userCoursesList.toString());  // Set the user's courses to the TextView
//        }
//    }
}
