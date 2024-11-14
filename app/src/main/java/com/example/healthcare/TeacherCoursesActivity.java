package com.example.healthcare;

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
            List<String> assignedCourses = database.getCoursesForUser(username);
            displayCourses(assignedCourses);
        } else {
            Log.d("TeacherCoursesActivity", "Username is missing from intent");
            Toast.makeText(this, "Username not provided", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayCourses(List<String> courses) {
        if (courses != null && !courses.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
            listViewCourses.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No courses assigned to this teacher", Toast.LENGTH_SHORT).show();
        }
    }
}
