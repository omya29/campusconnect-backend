package com.campusconnect.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusconnect.backend.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<Student> findByUsername(String username);

    List<Student> findByBranchIgnoreCase(String branch);
    List<Student> findByAcademicYearIgnoreCase(String academicYear);
}
