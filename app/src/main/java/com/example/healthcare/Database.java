package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    // Database details
    private static final String DATABASE_NAME = "healthcare.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String TABLE_MESSAGES = "messages";

    // Table columns for users
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role"; // Role column added

    // Table columns for messages
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SENDER_ID = "senderId";
    private static final String COLUMN_RECEIVER_ID = "receiverId";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    // SQL statement to create the users table
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_PASSWORD + " TEXT, " +
            COLUMN_ROLE + " TEXT);"; // Create table with role column

    // SQL statement to create the messages table
    private static final String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SENDER_ID + " INTEGER, " +
            COLUMN_RECEIVER_ID + " INTEGER, " +
            COLUMN_MESSAGE + " TEXT, " +
            COLUMN_TIMESTAMP + " TEXT);";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the users table
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        // Create the messages table
        sqLiteDatabase.execSQL(CREATE_MESSAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop older tables if they exist and recreate them
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(sqLiteDatabase);
    }

    // Method to register a user (now with role)
    public boolean register(String username, String email, String password, String role) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_ROLE, role); // Store the role in the database

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_USERS, null, cv);
        db.close();
        return result != -1; // Return true if insert was successful
    }

    // Method to login
    public boolean login(String username, String password) {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        // Check if a user exists with the given username and password
        boolean isValidUser = cursor.moveToFirst();

        // Close the cursor and database
        cursor.close();
        db.close();

        return isValidUser; // Return true if user exists, false otherwise
    }

    // Method to get the role of the user based on the username
    public String getUserRole(String username) {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query
        String query = "SELECT " + COLUMN_ROLE + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        String role = null;
        if (cursor.moveToFirst()) {
            // Retrieve the role of the user
            role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE));
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return role; // Return the role of the user
    }

    // Method to send a message
    public boolean sendMessage(int senderId, int receiverId, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SENDER_ID, senderId);
        values.put(COLUMN_RECEIVER_ID, receiverId);
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_TIMESTAMP, String.valueOf(System.currentTimeMillis()));

        long result = db.insert(TABLE_MESSAGES, null, values);
        db.close(); // Close the database after operation
        return result != -1; // Return true if insert was successful
    }

    // Method to get messages between two users
    @SuppressLint("Range")
    public List<String> getMessages(int userId, int otherUserId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> messages = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MESSAGES + " WHERE (senderId = ? AND receiverId = ?) OR (senderId = ? AND receiverId = ?)",
                new String[]{String.valueOf(userId), String.valueOf(otherUserId), String.valueOf(otherUserId), String.valueOf(userId)});

        while (cursor.moveToNext()) {
            messages.add(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE)));
        }
        cursor.close();
        db.close(); // Close the database after operation
        return messages;
    }
}
