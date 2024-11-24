package com.example.komodo_hub;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.example.komodo_hub.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubmitAssignmentActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private Uri fileUri;
    private EditText editTextAssignmentDetails;
    private Button buttonUpload, buttonSubmit;

}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_submit_assignment);
//
//        // Initialize UI components
//        editTextAssignmentDetails = findViewById(R.id.editTextAssignmentDetails);
//        buttonUpload = findViewById(R.id.buttonUpload);
//        buttonSubmit = findViewById(R.id.buttonSubmit);
//
//        // Set up the upload button click listener
//        buttonUpload.setOnClickListener(view -> {
//            // Create an intent to open the file picker
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*"); // Allows selection of any file type
//            startActivityForResult(Intent.createChooser(intent, "Select a file"), PICK_FILE_REQUEST);
//        });
//
//        // Set up the submit button click listener
//        buttonSubmit.setOnClickListener(view -> {
//            String assignmentDetails = editTextAssignmentDetails.getText().toString();
//
//            if (assignmentDetails.isEmpty()) {
//                Toast.makeText(SubmitAssignmentActivity.this, "Please enter assignment details.", Toast.LENGTH_SHORT).show();
//            } else if (fileUri == null) {
//                Toast.makeText(SubmitAssignmentActivity.this, "Please upload a file.", Toast.LENGTH_SHORT).show();
//            } else {
//                // Handle file upload and submission
//                new UploadAssignmentTask().execute(assignmentDetails, fileUri.toString());
//            }
//        });
//
//        // Handle window insets
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//
//    // Handle file selection
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
//            fileUri = data.getData();
//            if (fileUri != null) {
//                Toast.makeText(this, "File selected: " + fileUri.getPath(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // AsyncTask to handle file upload and submission
//    private class UploadAssignmentTask extends AsyncTask<String, Void, Boolean> {
//
//        @Override
//        protected Boolean doInBackground(String... params) {
//            String assignmentDetails = params[0];
//            String fileUriString = params[1];
//
//            // Implement file upload logic here (e.g., using HttpURLConnection or any networking library)
//            try {
//                URL url = new URL("https://your-server-url.com/upload");  // Replace with your server URL
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//
//                // Include file upload and form data in the request (not shown here for brevity)
//                // You would typically use a multipart request
//
//                // Send the request and handle the response
//                int responseCode = connection.getResponseCode();
//                return responseCode == HttpURLConnection.HTTP_OK;
//            } catch (IOException e) {
//                Log.e("SubmitAssignment", "Error uploading file", e);
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Boolean success) {
//            if (success) {
//                Toast.makeText(SubmitAssignmentActivity.this, "Assignment submitted successfully.", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(SubmitAssignmentActivity.this, "Failed to submit assignment.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}
