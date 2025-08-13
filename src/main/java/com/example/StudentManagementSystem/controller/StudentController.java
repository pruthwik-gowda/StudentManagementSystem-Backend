package com.example.StudentManagementSystem.controller;

import com.example.StudentManagementSystem.dto.StudentDetailDto;
import com.example.StudentManagementSystem.dto.StudentDto;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public Page<StudentDto> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "studentId") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam(required = false) String search
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (search != null && !search.isEmpty()) {
            return studentService.searchStudents(search, pageable);
        }

        return studentService.getAllStudents(pageable);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Integer id) {
        StudentDto student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/students/{id}/details")
    public ResponseEntity<StudentDetailDto> getStudentWithClasses(@PathVariable Integer id) {
        StudentDetailDto studentDetail = studentService.getStudentWithClasses(id);
        return ResponseEntity.ok(studentDetail);
    }

    // Using @ModelAttribute for form data
    @PostMapping("/students")
    public ResponseEntity<StudentDto> createStudent(@ModelAttribute Student student) {
        System.out.println("=== CREATE STUDENT WITH @ModelAttribute ===");
        System.out.println("Received student: " + student);
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Phone: " + student.getPhone());

        try {
            StudentDto createdStudent = studentService.createStudent(student);
            System.out.println("Student created successfully: " + createdStudent);
            return ResponseEntity.ok(createdStudent);
        } catch (Exception e) {
            System.err.println("Error creating student: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentDto> updateStudent(@ModelAttribute Student student, @PathVariable Integer id) {
        System.out.println("=== UPDATE STUDENT WITH @ModelAttribute ===");
        System.out.println("Path ID: " + id);
        System.out.println("Received student: " + student);
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Phone: " + student.getPhone());

        try {
            StudentDto updatedStudent = studentService.updateStudent(id, student);
            System.out.println("Student updated successfully: " + updatedStudent);
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

//    @PutMapping("/students/{id}")
//    public ResponseEntity<StudentDto> updateStudent(
//            @RequestBody Student student,
//            @PathVariable Integer id
//    ) {
//        StudentDto updatedStudent = studentService.updateStudent(id, student);
//        return ResponseEntity.ok(updatedStudent);
//    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}