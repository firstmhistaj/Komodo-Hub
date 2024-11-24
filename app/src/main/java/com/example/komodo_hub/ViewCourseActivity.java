package com.example.komodo_hub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healthcare.R;

import java.util.ArrayList;

public class ViewCourseActivity extends AppCompatActivity {

    ListView lvCourses;
    ArrayList<String> courseList;
    ArrayAdapter<String> courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable EdgeToEdge for immersive layout
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_course);

        // Handling system insets for immersive edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ListView for course list
        lvCourses = findViewById(R.id.lv_courses);

        // Creating a list of courses with sub-items
        courseList = new ArrayList<>();
        courseList.add("Maths");
        courseList.add("English");
        courseList.add("Biology");
        courseList.add("Computer Science");

        // Set the adapter for the ListView
        courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        lvCourses.setAdapter(courseAdapter);

        // Add a click listener to the ListView
        lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the clicked course
                String selectedCourse = courseList.get(position);

                // Redirect to CourseDetailActivity
                Intent intent = new Intent(ViewCourseActivity.this, CourseDetailActivity.class);
                intent.putExtra("courseName", selectedCourse); // Pass course name to the new activity
                startActivity(intent);
            }
        });
    }
}
