package com.example.StudentManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherWithClassesDto {
    private Integer teacherId;
    private String name;
    private String email;
    private String specialization;
    private List<ClassDto> classes;
}