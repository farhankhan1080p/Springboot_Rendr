package com.example.SpringMongoProject.Service;

import com.example.SpringMongoProject.Entity.Student;
import com.example.SpringMongoProject.Repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // For password hashing
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentServices {

    @Autowired
    private StudentRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Autowiring PasswordEncoder

    public void saveorUpdate(Student students) {
        students.setPassword(passwordEncoder.encode(students.getPassword()));
        System.out.println("Saving student: " + students.getEmail()); // Add debug line
        repo.save(students);
    }


    public Student getStudentByEmail(String email) {
        return repo.findByEmail(email);
    }

    // Validate login by comparing password
    public boolean validateLogin(String email, String password) {
        Student student = getStudentByEmail(email);
        if (student != null && passwordEncoder.matches(password, student.getPassword())) {
            return true; // Login successful
        }
        return false; // Login failed
    }

    public Iterable<Student> listAll() {

        return this.repo.findAll();
    }


    public void deleteStudent(String id) {

        repo.deleteById(id);
    }

    public Student getStudentByID(String studentid) {

        return repo.findById(studentid).get();
    }
}