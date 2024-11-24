package com.example.komodo_hub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;

public class ChatActivity extends AppCompatActivity {

    private TextView textViewChatHistory;
    private EditText editTextMessage;
    private Button buttonSend;
    private ScrollView scrollViewChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // Ensure you have this layout file created

        // Initialize views
        textViewChatHistory = findViewById(R.id.textViewChatHistory);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        scrollViewChat = findViewById(R.id.scrollViewChat);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String message = editTextMessage.getText().toString().trim();
        if (!message.isEmpty()) {
            // Append message to chat history
            String currentChat = textViewChatHistory.getText().toString();
            String newChat = currentChat + "\n" + message; // Add new message
            textViewChatHistory.setText(newChat); // Update chat history
            editTextMessage.setText(""); // Clear input field

            // Scroll to the bottom of the chat
            scrollViewChat.post(() -> scrollViewChat.fullScroll(View.FOCUS_DOWN));
        }
    }
}
