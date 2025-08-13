package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Student> findByEmailContainingIgnoreCase(String email, Pageable pageable);

    // Query to get student with their classes and teacher details
    @Query("SELECT s FROM Student s " +
            "LEFT JOIN FETCH s.classStudents cs " +
            "LEFT JOIN FETCH cs.classEntity c " +
            "LEFT JOIN FETCH c.teacher " +
            "WHERE s.studentId = :studentId")
    Optional<Student> findByIdWithClasses(@Param("studentId") Integer studentId);

    // Combined search for name, email, and phone
    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Student> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}