package com.campusconnect.backend.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.campusconnect.backend.entity.Student;
import com.campusconnect.backend.repository.StudentRepository;

@RestController
@RequestMapping("/api/uploads")
@CrossOrigin(origins = "*")
public class FileUploadController {

    // ✅ SAFE PATH (NO ERROR)
    private static final Path UPLOAD_DIR =
            Paths.get("C:/Users/lenovo/Desktop/campusconnect/uploads");

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/profile-photo/{username}")
    public String uploadProfilePhoto(@PathVariable String username,
                                     @RequestParam("file") MultipartFile file) {

        try {
            Optional<Student> opt = studentRepository.findByUsername(username);
            if (opt.isEmpty()) {
                return "Student not found";
            }

            // Create folder if not exists
            File folder = UPLOAD_DIR.toFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Generate unique filename
            String original = file.getOriginalFilename();
            String extension = "";

            if (original != null && original.contains(".")) {
                extension = original.substring(original.lastIndexOf("."));
            }

            String fileName = username + "_" + UUID.randomUUID() + extension;

            File destination = new File(folder, fileName);
            file.transferTo(destination);

            // Save filename in DB
            Student student = opt.get();
            student.setProfilePhoto(fileName);
            studentRepository.save(student);

            return "Profile photo uploaded successfully";

        } catch (IOException e) {
            e.printStackTrace();
            return "Upload failed: " + e.getMessage();
        }
    }
}
