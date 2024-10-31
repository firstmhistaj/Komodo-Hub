package com.example.healthcare;

import android.content.Intent; // Import this for Intent
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.cardview.widget.CardView; // Import CardView
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.databinding.ActivityStudentBinding;

public class StudentActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarStudent.toolbar);
        binding.appBarStudent.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Set up the AppBarConfiguration
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.editProfile, R.id.logout)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_student);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Find the CardView and set an OnClickListener
        CardView cardViewCourse = findViewById(R.id.cardViewCourse);
        cardViewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to start ViewCourseActivity
                Intent intent = new Intent(StudentActivity.this, ViewCourseActivity.class);
                startActivity(intent);
            }
        });

        CardView cardViewAssignments= findViewById(R.id.cardViewAssignments);
        cardViewAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to start ViewCourseActivity
                Intent intent = new Intent(StudentActivity.this, AssignmentsActivity.class);
                startActivity(intent);
            }
        });

        CardView cardSubmitAssignment= findViewById(R.id.cardSubmitAssignment);
        cardSubmitAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to start ViewCourseActivity
                Intent intent = new Intent(StudentActivity.this, SubmitAssignmentActivity.class);
                startActivity(intent);
            }
        });

        CardView cardViewGrades= findViewById(R.id.cardViewGrades);
        cardViewGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to start ViewCourseActivity
                Intent intent = new Intent(StudentActivity.this, ViewGradesActivity.class);
                startActivity(intent);
            }
        });

        CardView cardChat= findViewById(R.id.cardChat);
        cardChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to start ViewCourseActivity
                Intent intent = new Intent(StudentActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        CardView cardNews= findViewById(R.id.cardNews);
        cardNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to start ViewCourseActivity
                Intent intent = new Intent(StudentActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_student);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
