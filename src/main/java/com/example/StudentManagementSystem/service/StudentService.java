package com.example.StudentManagementSystem.service;

import com.example.StudentManagementSystem.dto.StudentDetailDto;
import com.example.StudentManagementSystem.dto.StudentDto;
import com.example.StudentManagementSystem.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    Page<StudentDto> getAllStudents(Pageable pageable);
    StudentDto getStudentById(Integer studentId);
    StudentDetailDto getStudentWithClasses(Integer studentId);
    StudentDto createStudent(Student student);
    StudentDto updateStudent(Integer studentId, Student student);
    void deleteStudent(Integer studentId);
    Page<StudentDto> searchStudents(String keyword, Pageable pageable);
}