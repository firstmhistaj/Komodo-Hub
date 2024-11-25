package com.example.komodo_hub;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthcare.R;
import java.util.HashMap;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private Database database;
    private RecyclerView studentsRecyclerView;
    private RecyclerView teachersRecyclerView;
    private Button backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Bind UI elements
        TextView totalUsersTextView = findViewById(R.id.totalUsersTextView);
        TextView totalCoursesTextView = findViewById(R.id.totalCoursesTextView);
        studentsRecyclerView = findViewById(R.id.studentsRecyclerView);
        teachersRecyclerView = findViewById(R.id.teachersRecyclerView);
        backButton = findViewById(R.id.backButton);

        // Initialize the database
        database = new Database(this);

        // Fetch user stats
        HashMap<String, Object> stats = database.getUserStats();

        int totalUserCount = (int) stats.getOrDefault("total_users", 0);
        List<HashMap<String, String>> students = (List<HashMap<String, String>>) stats.get("students");
        List<HashMap<String, String>> teachers = (List<HashMap<String, String>>) stats.get("teachers");

        // Update total users
        totalUsersTextView.setText("Total Users: " + totalUserCount);

        // Fetch and display total courses count
        int totalCourses = database.getTotalCoursesCount(); // Fetch total courses count
        totalCoursesTextView.setText("Total Courses: " + totalCourses); // Update UI with total courses

        // Set up RecyclerViews
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teachersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create and set the adapters for students and teachers
        StudentAdapter studentAdapter = new StudentAdapter(students, database);
        TeacherAdapter teacherAdapter = new TeacherAdapter(teachers, database);

        studentsRecyclerView.setAdapter(studentAdapter);
        teachersRecyclerView.setAdapter(teacherAdapter);

        // Back button functionality
        backButton.setOnClickListener(view -> finish());
    }
}
