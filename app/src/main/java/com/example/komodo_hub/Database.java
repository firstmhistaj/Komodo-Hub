package com.example.komodo_hub;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "komodo_hub.db";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_USERS = "users";
    private static final String TABLE_MESSAGES = "messages";
    private static final String TABLE_COURSES = "courses";
    private static final String TABLE_COURSE_ASSIGNMENTS = "course_assignments";
    private static final String TABLE_SCHOOLS = "schools";  // Added schools table
    private static final String TABLE_NEWS = "news";  // New news table

    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role"; // Role column added

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SENDER_ID = "senderId";
    private static final String COLUMN_RECEIVER_ID = "receiverId";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String COLUMN_COURSE_ID = "course_id";
    private static final String COLUMN_COURSE_NAME = "course_name";

    private static final String COLUMN_ASSIGNMENT_ID = "assignment_id";
    private static final String COLUMN_ASSIGNED_USER = "assigned_user";  // Username from TABLE_USERS
    private static final String COLUMN_ASSIGNED_COURSE_ID = "assigned_course_id"; // Course ID from TABLE_COURSES

    private static final String COLUMN_SCHOOL_ID = "school_id";
    private static final String COLUMN_SCHOOL_NAME = "school_name";  // School name

    // News Table
    private static final String COLUMN_NEWS_ID = "news_id";
    private static final String COLUMN_NEWS_TITLE = "news_title";
    private static final String COLUMN_NEWS_CONTENT = "news_content";
    private static final String COLUMN_NEWS_TIMESTAMP = "news_timestamp";
    private static final String COLUMN_NEWS_DESCRIPTION = "news_description";
    private static final String COLUMN_NEWS_LINK = "news_link";
    private static final String COLUMN_NEWS_FILE_PATH = "news_file_path";


    private static final String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_COURSES + " (" +
            COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_COURSE_NAME + " TEXT UNIQUE NOT NULL);";

    private static final String CREATE_COURSE_ASSIGNMENTS_TABLE = "CREATE TABLE " + TABLE_COURSE_ASSIGNMENTS + " (" +
            COLUMN_ASSIGNMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ASSIGNED_USER + " TEXT, " +
            COLUMN_ASSIGNED_COURSE_ID + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_ASSIGNED_USER + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USERNAME + "), " +
            "FOREIGN KEY(" + COLUMN_ASSIGNED_COURSE_ID + ") REFERENCES " + TABLE_COURSES + "(" + COLUMN_COURSE_ID + "));";

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

    private static final String CREATE_NEWS_TABLE = "CREATE TABLE " + TABLE_NEWS + " (" +
            COLUMN_NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NEWS_TITLE + " TEXT NOT NULL, " +
            COLUMN_NEWS_DESCRIPTION + " TEXT NOT NULL, " +  // Check if you have a field for description
            COLUMN_NEWS_CONTENT + " TEXT NOT NULL, " +  // Ensure content is not missing
            COLUMN_NEWS_LINK + " TEXT, " +
            COLUMN_NEWS_FILE_PATH + " TEXT);";


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);
        sqLiteDatabase.execSQL(CREATE_MESSAGES_TABLE);
        sqLiteDatabase.execSQL(CREATE_COURSES_TABLE);
        sqLiteDatabase.execSQL(CREATE_COURSE_ASSIGNMENTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_SCHOOLS_TABLE);
        sqLiteDatabase.execSQL(CREATE_NEWS_TABLE);  // Create news table

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
        Log.d("Database", "Upgrading database from version " + oldVersion + " to " + newVersion);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_ASSIGNMENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOLS);  // Drop schools table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);  // Drop news table on upgrade
        onCreate(sqLiteDatabase);
        Log.d("Database", "Database upgraded successfully from version " + oldVersion + " to " + newVersion);

    }



    public List<String> getCoursesForUser(String username) {
        List<String> courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT c." + COLUMN_COURSE_NAME +
                " FROM " + TABLE_COURSE_ASSIGNMENTS + " ca " +
                " JOIN " + TABLE_COURSES + " c ON ca." + COLUMN_ASSIGNED_COURSE_ID + " = c." + COLUMN_COURSE_ID +
                " WHERE ca." + COLUMN_ASSIGNED_USER + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{username});
        if (cursor.moveToFirst()) {
            do {
                String courseName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_NAME));
                courses.add(courseName);
            } while (cursor.moveToNext());
        }
        Log.d("Database", "Courses retrieved for user " + username + ": " + courses);
        cursor.close();
        db.close();
        return courses;
    }


    // Method to register a user (now with role)
    public boolean register(String username, String email, String password, String role) {
        // Create ContentValues object to store the user details
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_ROLE, role); // Store the role in the database
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_USERS, null, cv);

        if (result == -1) {
            Log.d("Database", "User registration failed for: " + username);
            db.close();
            return false; // Insert failed
        } else {
            Log.d("Database", "User registered successfully: " + username);
        }
        db.close();
        return true;
    }

    // Method to login
    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        boolean isValidUser = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValidUser;
    }

    // Method to get the role of the user based on the username
    public String getUserRole(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ROLE + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        String role = null;
        if (cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE));
        }
        cursor.close();
        db.close();
        return role;
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
        db.close();
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
        db.close();
        return messages;
    }

    public boolean addCourse(String courseName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COURSES + " WHERE " + COLUMN_COURSE_NAME + " = ?", new String[]{courseName});
        if (cursor.moveToFirst()) {
            Log.d("Database", "Course already exists: " + courseName);
            cursor.close();
            db.close();
            return false;
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
        db.close();
        return result != -1;
    }

    public boolean assignCourse(String username, String courseName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the course exists and get its ID
        Cursor courseCursor = db.rawQuery("SELECT " + COLUMN_COURSE_ID + " FROM " + TABLE_COURSES + " WHERE " + COLUMN_COURSE_NAME + " = ?", new String[]{courseName});
        if (!courseCursor.moveToFirst()) {
            Log.d("Database", "Course not found: " + courseName);
            courseCursor.close();
            db.close();
            return false;
        }
        int courseId = courseCursor.getInt(courseCursor.getColumnIndexOrThrow(COLUMN_COURSE_ID));
        courseCursor.close();

        // Check if the user exists
        Cursor userCursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});
        if (!userCursor.moveToFirst()) {
            Log.d("Database", "User not found: " + username);
            userCursor.close();
            db.close();
            return false;
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
            return false;
        }
        Log.d("Database", "Course assigned successfully: " + courseName + " to user: " + username);
        return true;
    }



    // Method to get all courses from the database
    public List<String> getAllCourses() {
        List<String> courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COURSES, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String courseName = cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_NAME));
                courses.add(courseName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
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
            return false;
        }
        cursor.close();
        // Insert new school
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCHOOL_NAME, schoolName);
        long result = db.insert(TABLE_SCHOOLS, null, values);
        db.close();
        return result != -1;
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

        if (newEmail != null) {
            values.put(COLUMN_EMAIL, newEmail);
        }
        if (newPassword != null) {
            values.put(COLUMN_PASSWORD, newPassword);
        }
        if (newRole != null) {
            values.put(COLUMN_ROLE, newRole);
        }
        if (values.size() == 0) {

            db.close();
            return false;
        }
        int rowsAffected = db.update(TABLE_USERS, values, COLUMN_USERNAME + " = ?", new String[]{username});
        db.close();
        return rowsAffected > 0;
    }


    public HashMap<String, Integer> getUserStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to count the number of students
        Cursor studentCursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLE + " = ?", new String[]{"student"});
        if (studentCursor.moveToFirst()) {
            int studentCount = studentCursor.getInt(0);
            stats.put("students", studentCount);
            Log.d("Database", "Number of students: " + studentCount);
        }
        studentCursor.close();

        // Query to count the number of teachers
        Cursor teacherCursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLE + " = ?", new String[]{"teacher"});
        if (teacherCursor.moveToFirst()) {
            int teacherCount = teacherCursor.getInt(0);
            stats.put("teachers", teacherCount);
            Log.d("Database", "Number of teachers: " + teacherCount);
        }
        teacherCursor.close();

        // Query to count the number of other roles (if needed)
        Cursor adminCursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLE + " = ?", new String[]{"admin"});
        if (adminCursor.moveToFirst()) {
            int adminCount = adminCursor.getInt(0);
            stats.put("admins", adminCount);
        }
        adminCursor.close();

        // Query to count total number of users
        Cursor totalUsersCursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERS, null);
        if (totalUsersCursor.moveToFirst()) {
            int totalUserCount = totalUsersCursor.getInt(0);
            stats.put("total_users", totalUserCount);
        }
        totalUsersCursor.close();

        db.close();
        return stats;
    }


    // Inside Database.java
    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE_ASSIGNMENTS, COLUMN_ASSIGNED_USER + " = ?", new String[]{username});
        db.delete(TABLE_MESSAGES, COLUMN_SENDER_ID + " = ?", new String[]{username});
        db.delete(TABLE_MESSAGES, COLUMN_RECEIVER_ID + " = ?", new String[]{username});
        int rowsDeleted = db.delete(TABLE_USERS, COLUMN_USERNAME + " = ?", new String[]{username});

        db.close();
        return rowsDeleted > 0;
    }


    // News Management
    public boolean addNews(String title, String description, String content, String link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NEWS_TITLE, title);
        values.put(COLUMN_NEWS_DESCRIPTION, description);
        values.put(COLUMN_NEWS_CONTENT, content);
        values.put(COLUMN_NEWS_LINK, link);
        values.put(COLUMN_NEWS_FILE_PATH, title);

        long result = db.insert(TABLE_NEWS, null, values);
        db.close();
        return result != -1;
    }

    @SuppressLint("Range")
    public List<String> getAllNews() {
        List<String> newsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS, null);
        while (cursor.moveToNext()) {
            newsList.add(cursor.getString(cursor.getColumnIndex(COLUMN_NEWS_TITLE)));
        }
        cursor.close();
        db.close();
        return newsList;
    }



    public boolean deleteNews(int newsId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NEWS, COLUMN_NEWS_ID + " = ?", new String[]{String.valueOf(newsId)});
        db.close();
        return rowsDeleted > 0;
    }

    // Inside Database.java
    public String[] getNewsDetailsByTitle(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEWS + " WHERE " + COLUMN_NEWS_TITLE + " = ?", new String[]{title});

        String[] newsDetails = null;

        if (cursor.moveToFirst()) {
            newsDetails = new String[]{
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NEWS_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NEWS_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NEWS_CONTENT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NEWS_LINK)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NEWS_FILE_PATH))
            };
        }

        cursor.close();
        db.close();
        return newsDetails;
    }


}



