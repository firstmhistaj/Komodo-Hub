package com.example.komodo_hub;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private EditText editTextNewsTitle, editTextNewsDescription, editTextNewsLink;
    private Button buttonUploadFile, buttonPostNews;
    private TextView textViewFilePath;
    private ListView newsListView;

    private Uri fileUri = null; // URI to hold the uploaded file
    private Database db; // Assuming a Database class for saving and retrieving news
    private List<String> newsTitles; // List to hold news titles

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Initialize UI components
        initUI();

        // Initialize database
        db = new Database(getApplicationContext());

        // Set up button listeners
        setupListeners();

        // Load the list of news from the database
        loadNewsList();
    }

    private void initUI() {
        editTextNewsTitle = findViewById(R.id.editTextNewsTitle);
        editTextNewsDescription = findViewById(R.id.editTextNewsDescription);
        editTextNewsLink = findViewById(R.id.editTextNewsLink);
        buttonUploadFile = findViewById(R.id.buttonUploadFile);
        textViewFilePath = findViewById(R.id.textViewFilePath);
        buttonPostNews = findViewById(R.id.buttonPostNews);
        newsListView = findViewById(R.id.newsListView);
    }

    private void setupListeners() {
        buttonUploadFile.setOnClickListener(v -> openFilePicker());
        buttonPostNews.setOnClickListener(v -> postNews());
    }

    private final ActivityResultLauncher<Intent> filePickerResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::onFileSelected);

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        filePickerResultLauncher.launch(intent);
    }

    private void onFileSelected(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            fileUri = result.getData() != null ? result.getData().getData() : null;
            if (fileUri != null) {
                String fileName = getFileNameFromUri(fileUri);
                textViewFilePath.setText(fileName != null ? fileName : "Unknown file");
            } else {
                textViewFilePath.setText("No file selected");
            }
        }
    }

    private void postNews() {
        String title = editTextNewsTitle.getText().toString().trim();
        String description = editTextNewsDescription.getText().toString().trim();
        String link = editTextNewsLink.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Title and Description are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        String filePath = fileUri != null ? fileUri.toString() : null;

        boolean success = db.addNews(title, description, link, filePath);
        if (success) {
            Toast.makeText(this, "News posted successfully!", Toast.LENGTH_SHORT).show();
            clearFields();
            loadNewsList();
        } else {
            Toast.makeText(this, "Failed to post news.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNewsList() {
        newsTitles = db.getAllNews(); // Fetch all news titles from the database
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newsTitles);
        newsListView.setAdapter(adapter);

        // Add click listener for the ListView items
        newsListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedNewsTitle = newsTitles.get(position); // Get the clicked news title
            openNewsDetailsActivity(selectedNewsTitle); // Open details activity
        });
    }

    private void openNewsDetailsActivity(String newsTitle) {
        // Fetch full details of the selected news from the database
        String[] newsDetails = db.getNewsDetailsByTitle(newsTitle); // Assume this method exists in your Database class

        if (newsDetails != null) {
            Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
            intent.putExtra("title", newsDetails[0]);
            intent.putExtra("description", newsDetails[1]);
            intent.putExtra("content", newsDetails[2]);
            intent.putExtra("link", newsDetails[3]);
            intent.putExtra("filePath", newsDetails[4]);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Details not found for the selected news.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editTextNewsTitle.setText("");
        editTextNewsDescription.setText("");
        editTextNewsLink.setText("");
        textViewFilePath.setText("No file selected");
    }

    @SuppressLint("Range")
    private String getFileNameFromUri(Uri uri) {
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                Log.e("NewsActivity", "Error retrieving file name", e);
            }
        } else if ("file".equals(uri.getScheme())) {
            return new File(uri.getPath()).getName();
        }
        return null;
    }
}
