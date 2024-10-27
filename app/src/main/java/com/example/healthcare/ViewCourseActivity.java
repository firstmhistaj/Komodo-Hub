package com.example.healthcare;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        courseList.add("1. 7099CEM\n   Machine Learning");
        courseList.add("2. 7100CEM\n   Big Data Analytics");
        courseList.add("3. 7101CEM\n   Cyber Security");

        // Set the adapter for the ListView
        courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        lvCourses.setAdapter(courseAdapter);
    }
}
