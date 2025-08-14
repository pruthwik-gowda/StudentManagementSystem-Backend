package com.example.StudentManagementSystem.service;

import com.example.StudentManagementSystem.dto.TeacherDto;
import com.example.StudentManagementSystem.dto.TeacherWithClassesDto;
import com.example.StudentManagementSystem.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService {

    Page<TeacherDto> getAllTeachers(Pageable pageable);
    TeacherDto getTeacherById(Integer teacherId);
    TeacherWithClassesDto getTeacherWithClasses(Integer teacherId);
    TeacherDto createTeacher(Teacher teacher);
    TeacherDto updateTeacher(Integer teacherId, Teacher teacher);
    void deleteTeacher(Integer teacherId);
    Page<TeacherDto> searchTeachers(String keyword, Pageable pageable);
}