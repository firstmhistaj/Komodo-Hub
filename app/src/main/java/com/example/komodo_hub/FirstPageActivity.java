package com.example.komodo_hub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;

public class FirstPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page); // Make sure this is the correct layout containing your button

        // Find the button by its ID
        Button button2Continue = findViewById(R.id.button2Continue);

        // Set an OnClickListener for the button
        button2Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is where you handle what happens when the button is clicked
                // For example, to start a new activity:
                Intent intent = new Intent(FirstPageActivity.this, LoginActivity.class);  // Replace ViewCourseActivity with the activity you want to navigate to
                startActivity(intent);
            }
        });
    }
}
