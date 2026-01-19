package com.campusconnect.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusconnect.backend.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
