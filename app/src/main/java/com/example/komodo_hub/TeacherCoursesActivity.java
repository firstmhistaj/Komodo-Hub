package com.example.komodo_hub;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;

import java.util.List;

public class TeacherCoursesActivity extends AppCompatActivity {

    private Database database;
    private ListView listViewCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_courses);

        listViewCourses = findViewById(R.id.listViewCourses);

        // Retrieve the username from the Intent
        String username = getIntent().getStringExtra("username");

        if (username != null) {
            database = new Database(this);

            // Fetch and display courses
            List<String> assignedCourses = getCoursesForUser(username);
            if (assignedCourses != null && !assignedCourses.isEmpty()) {
                displayCourses(assignedCourses);
            } else {
                // Handle the case where no courses are found
                Toast.makeText(this, "No courses assigned to this teacher", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("TeacherCoursesActivity", "Username is missing from intent");
            Toast.makeText(this, "Username not provided", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to fetch courses for the teacher from the Database
    private List<String> getCoursesForUser(String username) {
        try {
            // Fetch the list of courses assigned to the teacher
            return database.getCoursesForUser(username);
        } catch (Exception e) {
            Log.e("TeacherCoursesActivity", "Error fetching courses for user: " + username, e);
            Toast.makeText(this, "Error retrieving courses. Please try again.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    // Method to display the list of courses in the ListView
    private void displayCourses(List<String> courses) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
        listViewCourses.setAdapter(adapter);
    }
}
