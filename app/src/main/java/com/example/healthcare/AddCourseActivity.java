package com.example.healthcare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddCourseActivity extends AppCompatActivity {

    private Database database;
    private EditText editTextUsername, editTextCourse, editTextNewCourseName;
    private Button buttonAssignCourse, buttonAddCourse;
    private TextView textViewCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        database = new Database(this);

        // Initialize UI elements
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextCourse = findViewById(R.id.editTextCourse);
        editTextNewCourseName = findViewById(R.id.editTextNewCourseName);
        buttonAssignCourse = findViewById(R.id.buttonAssignCourse);
        buttonAddCourse = findViewById(R.id.buttonAddCourse);
        textViewCourses = findViewById(R.id.textViewCourses);

        // Set up "Assign Course" button functionality
        buttonAssignCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String courseName = editTextCourse.getText().toString().trim();


            }
        });



    }



}
