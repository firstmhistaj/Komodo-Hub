package com.example.komodo_hub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.R;

import java.util.List;
import java.util.HashMap;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private List<HashMap<String, String>> teachers;
    private Database database;

    public TeacherAdapter(List<HashMap<String, String>> teachers, Database database) {
        this.teachers = teachers;
        this.database = database;
    }

    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_item, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {
        HashMap<String, String> teacher = teachers.get(position);
        String username = teacher.get("username");

        // Fetch the courses assigned to the teacher
        List<String> teacherCourses = database.getCoursesAssignedToUser(username);

        // Display teacher info
        holder.firstnameTextView.setText("Firstname: " + teacher.get("firstname"));
        holder.lastnameTextView.setText("Lastname: " + teacher.get("lastname"));
        holder.usernameTextView.setText("Username: " + teacher.get("username"));
        holder.passwordTextView.setText("Password: " + teacher.get("password"));

        // Display the courses assigned to the teacher
        if (teacherCourses.isEmpty()) {
            holder.coursesTextView.setText("No courses assigned.");
        } else {
            holder.coursesTextView.setText("Courses: " + String.join(", ", teacherCourses));
        }
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView firstnameTextView, lastnameTextView, usernameTextView, passwordTextView, coursesTextView;

        public TeacherViewHolder(View itemView) {
            super(itemView);
            firstnameTextView = itemView.findViewById(R.id.teacherFirstNameTextView);
            lastnameTextView = itemView.findViewById(R.id.teacherLastNameTextView);
            usernameTextView = itemView.findViewById(R.id.teacherUsernameTextView);
            passwordTextView = itemView.findViewById(R.id.teacherPasswordTextView);
            coursesTextView = itemView.findViewById(R.id.teacherCoursesTextView);
        }
    }
}
