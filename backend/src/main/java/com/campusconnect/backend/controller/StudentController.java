package com.campusconnect.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.campusconnect.backend.entity.Student;
import com.campusconnect.backend.repository.StudentRepository;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // ================= REGISTER =================
    @PostMapping("/register")
    public String registerStudent(@RequestBody Student student) {

        if (studentRepository.existsByUsername(student.getUsername())) {
            return "Username already exists";
        }

        if (studentRepository.existsByEmail(student.getEmail())) {
            return "Email already exists";
        }

        studentRepository.save(student);
        return "Registration successful";
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public String loginStudent(@RequestBody Student student) {

        Optional<Student> existingStudent =
                studentRepository.findByUsername(student.getUsername());

        if (existingStudent.isEmpty()) {
            return "Invalid username or password";
        }

        if (!existingStudent.get().getPassword().equals(student.getPassword())) {
            return "Invalid username or password";
        }

        return "Login successful";
    }

    // ================= TEST =================
    @GetMapping("/test")
    public String testApi() {
        return "Student API Working";
    }

    // ================= SEARCH =================

    // SEARCH BY BRANCH
    @GetMapping("/search/branch/{branch}")
    public List<Student> searchByBranch(@PathVariable String branch) {
        return studentRepository.findByBranchIgnoreCase(branch);
    }

    // SEARCH BY ACADEMIC YEAR
    @GetMapping("/search/year/{year}")
    public List<Student> searchByYear(@PathVariable String year) {
        return studentRepository.findByAcademicYearIgnoreCase(year);
    }
    
    @GetMapping("/user/{username}")
    public Student getStudentByUsername(@PathVariable String username) {
        return studentRepository.findByUsername(username).orElse(null);
    }

}
