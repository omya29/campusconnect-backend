package com.campusconnect.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.campusconnect.backend.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // ✅ Chat between two users
    @Query("""
        SELECT m FROM Message m
        WHERE (m.senderUsername = ?1 AND m.receiverUsername = ?2)
           OR (m.senderUsername = ?2 AND m.receiverUsername = ?1)
        ORDER BY m.createdAt ASC
    """)
    List<Message> getChat(String user1, String user2);

    // ✅ List of users chatted with (LEFT SIDEBAR)
    @Query("""
        SELECT DISTINCT 
            CASE 
                WHEN m.senderUsername = ?1 THEN m.receiverUsername
                ELSE m.senderUsername
            END
        FROM Message m
        WHERE m.senderUsername = ?1 OR m.receiverUsername = ?1
    """)
    List<String> getChatUsers(String username);
}
