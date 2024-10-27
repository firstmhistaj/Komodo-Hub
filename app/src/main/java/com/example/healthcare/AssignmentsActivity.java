package com.example.healthcare; // Replace with your actual package name

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AssignmentsActivity extends AppCompatActivity {

    private ListView listAssignments;
    private TextView textViewAssignment;
    private ArrayList<String> assignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments); // Make sure this matches your XML filename

        // Initialize the views
        listAssignments = findViewById(R.id.listAssignments);
        textViewAssignment = findViewById(R.id.textViewAssignment);

        // Set text for the title TextView (optional)
        textViewAssignment.setText("Assignments");

        // Initialize the list of assignments
        assignments = new ArrayList<>();
        assignments.add("Math Homework");
        assignments.add("Science Project");
        assignments.add("History Essay");
        assignments.add("English Reading");
        assignments.add("Computer Science Assignment");
        assignments.add("Art Project");

        // Create an ArrayAdapter to bind the data to the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, assignments);

        // Set the adapter to the ListView
        listAssignments.setAdapter(adapter);
    }
}
