package com.example.healthcare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class TeacherCoursesActivity extends AppCompatActivity {

    private Database database;
    private ListView listViewCourses;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        // Initialize database and UI elements
        database = new Database(this);
        listViewCourses = findViewById(R.id.listViewCourses);

        String loggedInUsername = getIntent().getStringExtra("username"); // Make sure to pass this from the login activity


        // Retrieve assigned courses for debugging
        List<String> assignedCourses = database.getCoursesForUser(loggedInUsername);
        Log.d("TeacherCoursesActivity", "Assigned Courses: " + assignedCourses);

        // Display courses or a toast message if no courses found
        if (assignedCourses.isEmpty()) {
            Toast.makeText(this, "No courses assigned", Toast.LENGTH_SHORT).show();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assignedCourses);
            listViewCourses.setAdapter(adapter);
        }
    }
}
