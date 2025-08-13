package com.example.StudentManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailDto {
    private Integer studentId;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private List<StudentClassDto> classes;
}