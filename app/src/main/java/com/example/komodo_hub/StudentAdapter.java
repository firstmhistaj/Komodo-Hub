package com.example.komodo_hub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.R;

import java.util.List;
import java.util.HashMap;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<HashMap<String, String>> students;
    private Database database;

    public StudentAdapter(List<HashMap<String, String>> students, Database database) {
        this.students = students;
        this.database = database;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        HashMap<String, String> student = students.get(position);
        String username = student.get("username");

        // Fetch the courses assigned to the student
        List<String> studentCourses = database.getCoursesAssignedToUser(username);

        // Display student info
        holder.firstnameTextView.setText("Firstname: " + student.get("firstname"));
        holder.lastnameTextView.setText("Lastname: " + student.get("lastname"));
        holder.usernameTextView.setText("Username: " + student.get("username"));
        holder.passwordTextView.setText("Password: " + student.get("password"));

        // Display the courses assigned to the student
        if (studentCourses.isEmpty()) {
            holder.coursesTextView.setText("No courses assigned.");
        } else {
            holder.coursesTextView.setText("Courses: " + String.join(", ", studentCourses));
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView firstnameTextView, lastnameTextView, usernameTextView, passwordTextView, coursesTextView;

        public StudentViewHolder(View itemView) {
            super(itemView);
            firstnameTextView = itemView.findViewById(R.id.studentFirstNameTextView);
            lastnameTextView = itemView.findViewById(R.id.studentLastNameTextView);
            usernameTextView = itemView.findViewById(R.id.studentUsernameTextView);
            passwordTextView = itemView.findViewById(R.id.studentPasswordTextView);
            coursesTextView = itemView.findViewById(R.id.studentCoursesTextView);
        }
    }
}
