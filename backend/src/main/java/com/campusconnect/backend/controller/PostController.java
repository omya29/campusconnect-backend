package com.campusconnect.backend.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.campusconnect.backend.entity.Post;
import com.campusconnect.backend.repository.PostRepository;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/upload")
    public String uploadPost(
            @RequestParam("username") String username,
            @RequestParam("caption") String caption,
            @RequestParam("image") MultipartFile image
    ) throws Exception {

        // Create uploads folder
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File folder = new File(uploadDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Save file
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        File file = new File(uploadDir + fileName);
        image.transferTo(file);

        // Save in DB
        Post post = new Post();
        post.setUsername(username);
        post.setCaption(caption);
        post.setImageUrl("/uploads/" + fileName);

        postRepository.save(post);

        return "Post uploaded successfully";
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
