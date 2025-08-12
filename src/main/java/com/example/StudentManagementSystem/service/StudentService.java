package com.example.StudentManagementSystem.service;

import com.example.StudentManagementSystem.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    Page<Student> getAllStudents(Pageable pageable);
    Student getStudentById(Integer id);
    Student createStudent(Student student);
    Student updateStudent(Integer id, Student student);
    void deleteStudent(Integer id);
    Page<Student> searchStudents(String keyword, Pageable pageable);
}
