package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class TeacherActivity extends AppCompatActivity {

    // Declare the CardView variables for each dashboard item
    private CardView cardCourse, cardAssignments, cardSubmission, cardTrackStudentProgress, cardChat, cardNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);  // Layout file containing your XML code

        // Initialize CardViews by finding them by their IDs
        cardCourse = findViewById(R.id.cardCourse);
        cardAssignments = findViewById(R.id.cardAssignments);
        cardSubmission = findViewById(R.id.cardSubmission);
        cardTrackStudentProgress = findViewById(R.id.cardTrackStudentProgress);
        cardChat = findViewById(R.id.cardChat);
        cardNews = findViewById(R.id.cardNews);

        // Set up listeners for each card

        cardAssignments.setOnClickListener(view -> openAssignmentsSection());

        cardTrackStudentProgress.setOnClickListener(view -> openTrackProgressSection());

        cardNews.setOnClickListener(view -> openNewsSection());

        cardChat.setOnClickListener(view -> openChatSection());

        cardCourse.setOnClickListener(view -> openCourseSection());

        cardSubmission.setOnClickListener(view -> openSubmissionSection());
    }

    // Methods to open each section - here just examples, implement each as needed



    private void openAssignmentsSection() {
        Intent intent = new Intent(TeacherActivity.this, AssignmentsActivity.class);
        startActivity(intent);
    }


    private void openTrackProgressSection() {
        Intent intent = new Intent(TeacherActivity.this, TrackProgressActivity.class);
        startActivity(intent);
    }



    private void openNewsSection() {
        Intent intent = new Intent(TeacherActivity.this, NewsActivity.class);
        startActivity(intent);
    }

    private void openChatSection() {
        Intent intent = new Intent(TeacherActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    private void openCourseSection() {
        Intent intent = new Intent(TeacherActivity.this, CourseActivity.class);
        startActivity(intent);
    }

    private void openSubmissionSection() {
        Intent intent = new Intent(TeacherActivity.this, SubmissionActivity.class);
        startActivity(intent);
    }
}
