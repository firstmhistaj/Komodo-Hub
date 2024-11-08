package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    // Database details
    private static final String DATABASE_NAME = "komodo_hub.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_USERS = "users";
    private static final String TABLE_MESSAGES = "messages";
    private static final String TABLE_COURSES = "courses";
    private static final String TABLE_COURSE_ASSIGNMENTS = "course_assignments";
    private static final String TABLE_SCHOOLS = "schools";  // Added schools table



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

    // Table columns for courses
    private static final String COLUMN_COURSE_ID = "course_id";
    private static final String COLUMN_COURSE_NAME = "course_name";

    // Define new table and columns for course assignments
    private static final String COLUMN_ASSIGNMENT_ID = "assignment_id";
    private static final String COLUMN_ASSIGNED_USER = "assigned_user";  // Username from TABLE_USERS
    private static final String COLUMN_ASSIGNED_COURSE_ID = "assigned_course_id"; // Course ID from TABLE_COURSES

    // Table columns for schools (new table)
    private static final String COLUMN_SCHOOL_ID = "school_id";
    private static final String COLUMN_SCHOOL_NAME = "school_name";  // School name


    // SQL statement to create the courses table
    private static final String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_COURSES + " (" +
            COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_COURSE_NAME + " TEXT UNIQUE NOT NULL);";

    // SQL to create the course assignments table
    private static final String CREATE_COURSE_ASSIGNMENTS_TABLE = "CREATE TABLE " + TABLE_COURSE_ASSIGNMENTS + " (" +
            COLUMN_ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ASSIGNED_USER + " TEXT, " +
            COLUMN_ASSIGNED_COURSE_ID + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_ASSIGNED_USER + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERNAME + "), " +
            "FOREIGN KEY(" + COLUMN_ASSIGNED_COURSE_ID + ") REFERENCES " + TABLE_COURSES + "(" + COLUMN_COURSE_ID + "));";

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


    // SQL statement to create the schools table (new table)
    private static final String CREATE_SCHOOLS_TABLE = "CREATE TABLE " + TABLE_SCHOOLS + " (" +
            COLUMN_SCHOOL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SCHOOL_NAME + " TEXT UNIQUE NOT NULL);";


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the users table
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        // Create the messages table
        sqLiteDatabase.execSQL(CREATE_MESSAGES_TABLE);
        // Create the courses table
        sqLiteDatabase.execSQL(CREATE_COURSES_TABLE);
        // Create assignments table
        sqLiteDatabase.execSQL(CREATE_COURSE_ASSIGNMENTS_TABLE);
        // Create schools table
        sqLiteDatabase.execSQL(CREATE_SCHOOLS_TABLE);



        // Insert default system admin if it doesn't exist
        insertDefaultSystemAdmin(sqLiteDatabase);
        Log.d("Database", "Tables created successfully");

    }

    private void insertDefaultSystemAdmin(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{"system_admin"});

        if (!cursor.moveToFirst()) {
            // Insert default system admin if it does not exist
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, "system_admin");
            values.put(COLUMN_PASSWORD, "Nigeria01@"); // Default password for system admin
            values.put(COLUMN_ROLE, "system_admin");
            db.insert(TABLE_USERS, null, values);
            Log.d("Database", "Default system admin inserted.");
        }
        cursor.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop older tables if they exist and recreate them
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_ASSIGNMENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOLS);  // Drop schools table

        onCreate(sqLiteDatabase);
        Log.d("Database", "Database upgraded successfully from version " + oldVersion + " to " + newVersion);

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

    public boolean addCourse(String courseName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the course already exists
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COURSES + " WHERE " + COLUMN_COURSE_NAME + " = ?", new String[]{courseName});
        if (cursor.moveToFirst()) {
            Log.d("Database", "Course already exists: " + courseName);
            cursor.close();
            db.close();
            return false; // Course already exists
        }
        cursor.close();

        // Insert new course
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, courseName);
        long result = db.insert(TABLE_COURSES, null, values);

        if (result != -1) {
            Log.d("Database", "Course added successfully: " + courseName);
        } else {
            Log.d("Database", "Failed to add course: " + courseName);
        }
        db.close(); // Close the database after operation

        return result != -1; // Return true if insert was successful
    }

    public boolean assignCourse(String username, String courseName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the course exists and get its ID
        Cursor courseCursor = db.rawQuery("SELECT " + COLUMN_COURSE_ID + " FROM " + TABLE_COURSES + " WHERE " + COLUMN_COURSE_NAME + " = ?", new String[]{courseName});
        if (!courseCursor.moveToFirst()) {
            Log.d("Database", "Course not found: " + courseName);
            courseCursor.close();
            db.close();
            return false; // Course does not exist
        }
        int courseId = courseCursor.getInt(courseCursor.getColumnIndexOrThrow(COLUMN_COURSE_ID));
        courseCursor.close();

        // Check if the user exists
        Cursor userCursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});
        if (!userCursor.moveToFirst()) {
            Log.d("Database", "User not found: " + username);
            userCursor.close();
            db.close();
            return false; // User does not exist
        }
        userCursor.close();

        // Assign course to user
        ContentValues values = new ContentValues();
        values.put(COLUMN_ASSIGNED_USER, username);
        values.put(COLUMN_ASSIGNED_COURSE_ID, courseId);
        long result = db.insert(TABLE_COURSE_ASSIGNMENTS, null, values);
        db.close();

        if (result == -1) {
            Log.d("Database", "Failed to assign course: " + courseName + " to user: " + username);
            return false; // Failed to assign the course
        }

        Log.d("Database", "Course assigned successfully: " + courseName + " to user: " + username);
        return true; // Assignment was successful
    }

    // Method to retrieve courses assigned to a specific teacher
    public List<String> getAssignedCourses(String teacherUsername) {
        List<String> courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Assuming you have a table named "courses" with a column for teacher username
        String query = "SELECT course_name FROM courses WHERE teacher_username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{teacherUsername});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String courseName = cursor.getString(cursor.getColumnIndex("course_name"));
                courses.add(courseName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courses;
    }


    // Method to add a school
    public boolean addSchool(String schoolName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the school already exists
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SCHOOLS + " WHERE " + COLUMN_SCHOOL_NAME + " = ?", new String[]{schoolName});
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return false;  // School already exists
        }
        cursor.close();

        // Insert new school
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCHOOL_NAME, schoolName);
        long result = db.insert(TABLE_SCHOOLS, null, values);
        db.close();
        return result != -1;  // Return true if insert was successful
    }

    // Method to get all schools
    public List<String> getAllSchools() {
        List<String> schools = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SCHOOLS, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String schoolName = cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL_NAME));
                schools.add(schoolName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return schools;
    }

    // Method to update user details in the database
    public boolean updateUser(String username, String newEmail, String newPassword, String newRole) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Add values to ContentValues only if they are not null
        if (newEmail != null) {
            values.put(COLUMN_EMAIL, newEmail);
        }
        if (newPassword != null) {
            values.put(COLUMN_PASSWORD, newPassword);
        }
        if (newRole != null) {
            values.put(COLUMN_ROLE, newRole);
        }

        // Check if there is anything to update
        if (values.size() == 0) {
            // No values provided to update, return false
            db.close();
            return false;
        }

        // Execute the update operation
        int rowsAffected = db.update(TABLE_USERS, values, COLUMN_USERNAME + " = ?", new String[]{username});
        db.close(); // Close the database after the operation

        // Return true if at least one row was updated
        return rowsAffected > 0;
    }
}
