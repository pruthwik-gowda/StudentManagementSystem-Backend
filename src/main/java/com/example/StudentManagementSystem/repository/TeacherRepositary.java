package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepositary extends JpaRepository<Teacher, Integer> {
    // Additional query methods can be defined here if needed


}
