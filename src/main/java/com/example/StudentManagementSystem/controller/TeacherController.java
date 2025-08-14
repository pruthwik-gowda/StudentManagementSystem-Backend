package com.example.StudentManagementSystem.controller;


import com.example.StudentManagementSystem.dto.TeacherDto;
import com.example.StudentManagementSystem.dto.TeacherWithClassesDto;
import com.example.StudentManagementSystem.dto.UpdateTeacherDto;
import com.example.StudentManagementSystem.entity.Teacher;
import com.example.StudentManagementSystem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    Page<TeacherDto> getAllTeachers(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "teacherId") String sortBy,
            @RequestParam (defaultValue = "true") boolean ascending,
            @RequestParam (required = false) String search
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (search != null && !search.isEmpty()) {
            return teacherService.searchTeachers(search, pageable);
        }
        return teacherService.getAllTeachers(pageable);
    }

    @GetMapping("/{teacherId}")
    ResponseEntity<TeacherDto> getTeacherById(@PathVariable Integer teacherId) {
        TeacherDto teacher = teacherService.getTeacherById(teacherId);
        return teacher != null ? ResponseEntity.ok(teacher) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{teacherId}/details")
    ResponseEntity<TeacherWithClassesDto> getTeacherWithClasses(@PathVariable Integer teacherId) {
        try {
            TeacherWithClassesDto teacherWithClasses = teacherService.getTeacherWithClasses(teacherId);
            return ResponseEntity.ok(teacherWithClasses);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<TeacherDto> createTeacher(@RequestBody UpdateTeacherDto teacher) {

        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setName(teacher.getName());
        updatedTeacher.setEmail(teacher.getEmail());
        updatedTeacher.setSpecialization(teacher.getSpecialization());

        TeacherDto teacherDto = teacherService.createTeacher(updatedTeacher);

        return ResponseEntity.ok(teacherDto);
    }

    @PutMapping("/{teacherId}")
    ResponseEntity<TeacherDto> updateTeacher(@PathVariable Integer teacherId, @RequestBody UpdateTeacherDto teacher) {

        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setName(teacher.getName());
        updatedTeacher.setEmail(teacher.getEmail());
        updatedTeacher.setSpecialization(teacher.getSpecialization());

        TeacherDto teacherDto = teacherService.updateTeacher(teacherId, updatedTeacher);

        return ResponseEntity.ok(teacherDto);
    }

    @DeleteMapping("/{teacherId}")
    ResponseEntity<Void> deleteTeacher(@PathVariable Integer teacherId) {
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.noContent().build();
    }

}
