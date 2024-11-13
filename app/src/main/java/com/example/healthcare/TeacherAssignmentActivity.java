package com.example.healthcare; // Replace with your actual package name

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TeacherAssignmentActivity extends AppCompatActivity {

    private ListView listAssignments;
    private EditText etAssignmentTitle, etAssignmentDescription; // EditTexts for user input
    private Button btnPostAssignment; // Button to post the assignment
    private ArrayList<String> assignments; // List to hold the assignments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assignment); // Ensure this matches your XML filename

        // Initialize the views
        listAssignments = findViewById(R.id.listAssignments);
        etAssignmentTitle = findViewById(R.id.et_assignment_title); // EditText for assignment title
        etAssignmentDescription = findViewById(R.id.et_assignment_description); // EditText for assignment description
        btnPostAssignment = findViewById(R.id.btn_post_assignment); // Button to post the assignment

        // Initialize the list of assignments
        assignments = new ArrayList<>();
        // Sample data for testing
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

        // Set up the post assignment button click listener
        btnPostAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the title and description from the EditTexts
                String title = etAssignmentTitle.getText().toString().trim();
                String description = etAssignmentDescription.getText().toString().trim();

                // Check if both fields are not empty
                if (!title.isEmpty() && !description.isEmpty()) {
                    // Create a new assignment and add it to the list
                    String newAssignment = "Title: " + title + "\nDescription: " + description;
                    assignments.add(newAssignment);

                    // Notify the adapter that the data has changed
                    ((ArrayAdapter) listAssignments.getAdapter()).notifyDataSetChanged();

                    // Clear the input fields after posting
                    etAssignmentTitle.setText("");
                    etAssignmentDescription.setText("");

                    // Show a confirmation message
                    Toast.makeText(TeacherAssignmentActivity.this, "Assignment Posted", Toast.LENGTH_SHORT).show();
                } else {
                    // Show a message if fields are empty
                    Toast.makeText(TeacherAssignmentActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
