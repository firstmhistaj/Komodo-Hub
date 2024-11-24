package com.example.komodo_hub;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;

public class NewsDetailsActivity extends AppCompatActivity {

    private TextView textViewTitle, textViewDescription, textViewContent, textViewLink, textViewFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        initUI();

        // Get the news details from the Intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String content = getIntent().getStringExtra("content");
        String link = getIntent().getStringExtra("link");
        String filePath = getIntent().getStringExtra("filePath");

        // Populate the UI with the news details
        textViewTitle.setText("Title: " + title);
        textViewDescription.setText("Description: " + description);
        textViewContent.setText("Content: " + content);
        textViewLink.setText("Link: " + link);
        textViewFile.setText("File: " + (filePath != null ? filePath : "No file uploaded"));
    }

    private void initUI() {
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewContent = findViewById(R.id.textViewContent);
        textViewLink = findViewById(R.id.textViewLink);
        textViewFile = findViewById(R.id.textViewFile);
    }
}
