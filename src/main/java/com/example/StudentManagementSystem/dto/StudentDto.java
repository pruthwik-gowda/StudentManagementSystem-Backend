package com.example.StudentManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Integer studentId;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
}