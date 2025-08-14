package com.example.StudentManagementSystem.service;


import com.example.StudentManagementSystem.dto.ClassDto;
import com.example.StudentManagementSystem.dto.TeacherDto;
import com.example.StudentManagementSystem.dto.TeacherWithClassesDto;
import com.example.StudentManagementSystem.entity.Class;
import com.example.StudentManagementSystem.entity.Teacher;
import com.example.StudentManagementSystem.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }


    @Override
    public Page<TeacherDto> getAllTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable).map(this::convertToDto);
    }

    @Override
    public TeacherDto getTeacherById(Integer teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        return teacher != null ? convertToDto(teacher) : null;
    }

    @Override
    public TeacherDto createTeacher(Teacher teacher) {
        Teacher savedTeacher = teacherRepository.save(teacher);
        return convertToDto(savedTeacher);
    }

    @Override
    public TeacherDto updateTeacher(Integer teacherId, Teacher teacher) {
        Teacher existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + teacherId));

        existingTeacher.setName(teacher.getName());
        existingTeacher.setEmail(teacher.getEmail());
        existingTeacher.setSpecialization(teacher.getSpecialization());

        Teacher updatedTeacher = teacherRepository.save(existingTeacher);
        return convertToDto(updatedTeacher);

    }

    @Override
    public void deleteTeacher(Integer teacherId) {
        if(!teacherRepository.existsById(teacherId)) {
            throw new RuntimeException("Teacher not found with id: " + teacherId);
        }
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public Page<TeacherDto> searchTeachers(String keyword, Pageable pageable) {
        return teacherRepository.findByKeyword(keyword, pageable).map(this::convertToDto);
    }

    @Override
    public TeacherWithClassesDto getTeacherWithClasses(Integer teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));

        return convertToTeacherWithClassesDto(teacher);
    }

    private TeacherDto convertToDto(Teacher teacher) {
        return new TeacherDto(
                teacher.getTeacherId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getSpecialization()
        );
    }

    private TeacherWithClassesDto convertToTeacherWithClassesDto(Teacher teacher) {
        List<ClassDto> classDtos = teacher.getClasses() != null ?
                teacher.getClasses().stream()
                        .map(this::convertToClassDto)
                        .collect(Collectors.toList()) :
                List.of();

        return new TeacherWithClassesDto(
                teacher.getTeacherId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getSpecialization(),
                classDtos
        );
    }

    private ClassDto convertToClassDto(Class classEntity) {
        return new ClassDto(
                classEntity.getClassId(),
                classEntity.getTopic(),
                classEntity.getStartTime(),
                classEntity.getEndTime()
        );
    }
}
