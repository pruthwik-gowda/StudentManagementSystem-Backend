package com.example.StudentManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTeacherDto {
    private String name;          // FIX: Changed from public to private for proper encapsulation
    private String email;         // FIX: Changed from public to private for proper encapsulation
    private String specialization; // FIX: Changed from public to private for proper encapsulation
}