package com.example.komodo_hub;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;

import java.util.HashMap;

public class StatsActivity extends AppCompatActivity {

    private Database database;
    private TextView statsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);  // Assuming you have a layout for this activity

        statsTextView = findViewById(R.id.statsTextView);  // Assuming you have a TextView to display the stats

        // Initialize the database
        database = new Database(this);

        // Fetch user stats
        HashMap<String, Integer> stats = database.getUserStats();

        // Display the stats
//        int studentCount = stats.getOrDefault("students", 0);
//        int teacherCount = stats.getOrDefault("teachers", 0);
        int adminCount = stats.getOrDefault("admins", 0);
        int totalUserCount = stats.getOrDefault("total_users", 0);

        String statsText = "Total Users: " + totalUserCount + "\n" +
//                "Total Students: " + studentCount + "\n" +
//                "Total Teachers: " + teacherCount + "\n" +
                "Total Admins: " + adminCount;

        statsTextView.setText(statsText);
    }
}
