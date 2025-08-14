package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
// FIX: Removed unused imports (TeacherDto, Student)

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    // Find teachers by name containing the search term (case insensitive)
    Page<Teacher> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT t FROM Teacher t WHERE " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.specialization) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Teacher> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}