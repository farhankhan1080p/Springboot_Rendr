package com.example.SpringMongoProject.Controller;

import com.example.SpringMongoProject.Entity.Student;
import com.example.SpringMongoProject.Service.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // For password hashing


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/student")
public class StudentController {

    @Autowired
    private StudentServices studentServices;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Signup endpoint
    @PostMapping("/signup")
    public String signup(@RequestBody Student student) {
        if (studentServices.getStudentByEmail(student.getEmail()) != null) {
            return "Email already exists!";
        }
        studentServices.saveorUpdate(student);
        return "Signup successful";
    }

    // Login endpoint
    @PostMapping("/login")
    public String login(@RequestBody Student student) {
        Student existingStudent = studentServices.getStudentByEmail(student.getEmail());

        if (existingStudent != null && passwordEncoder.matches(student.getPassword(), existingStudent.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }

    @PostMapping(value = "/save")
    private String saveStudent(@RequestBody Student students) {

        studentServices.saveorUpdate(students);
        return students.get_id();
    }

    @GetMapping(value = "/getall")
    public Iterable<Student> getStudents() {
        return studentServices.listAll();
    }

    @PutMapping(value = "/edit/{id}")
    private Student update(@RequestBody Student student, @PathVariable(name = "id") String _id) {
        student.set_id(_id);
        studentServices.saveorUpdate(student);
        return student;
    }

    @DeleteMapping("/delete/{id}")
    private void deleteStudent(@PathVariable("id") String _id) {
        studentServices.deleteStudent(_id);
    }


    @RequestMapping("/search/{id}")
    private Student getStudents(@PathVariable(name = "id") String studentid) {
        return studentServices.getStudentByID(studentid);
    }

}