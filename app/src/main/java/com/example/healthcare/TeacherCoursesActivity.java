package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TeacherCoursesActivity extends AppCompatActivity {
    private Database db;
    private ListView listViewCourses;
    private String teacherUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_courses);  // Ensure this XML matches your layout file name

        // Get the username passed from the previous activity (e.g., login activity)
        Intent intent = getIntent();
        teacherUsername = intent.getStringExtra("username");

        listViewCourses = findViewById(R.id.listViewCourses);
        db = new Database(this);

        // Fetch and display assigned courses
        displayAssignedCourses();
    }

    private void displayAssignedCourses() {
        if (teacherUsername == null || teacherUsername.isEmpty()) {
            Toast.makeText(this, "Invalid user. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("TeacherCoursesActivity", "Fetching assigned courses for teacher: " + teacherUsername);

        // Fetch courses assigned to the teacher from the database
        List<String> assignedCourses = db.getAssignedCourses(teacherUsername);

        if (assignedCourses.isEmpty()) {
            Toast.makeText(this, "No courses assigned to you.", Toast.LENGTH_SHORT).show();
        } else {
            // Display the list of courses in the ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assignedCourses);
            listViewCourses.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        db.close(); // Close the database when the activity is destroyed
        super.onDestroy();
    }
}
