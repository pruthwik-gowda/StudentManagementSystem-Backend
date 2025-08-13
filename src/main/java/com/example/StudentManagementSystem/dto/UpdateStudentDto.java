package com.example.StudentManagementSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentDto {
    // Clear what can be updated
    private String name;
    private String email;
    private String phone;
}
