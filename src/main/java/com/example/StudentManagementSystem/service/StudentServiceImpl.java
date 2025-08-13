package com.example.StudentManagementSystem.service;

import com.example.StudentManagementSystem.dto.StudentDetailDto;
import com.example.StudentManagementSystem.dto.StudentDto;
import com.example.StudentManagementSystem.dto.StudentClassDto;
import com.example.StudentManagementSystem.dto.TeacherDto;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDto> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto getStudentById(Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        return convertToDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDetailDto getStudentWithClasses(Integer studentId) {
        Student student = studentRepository.findByIdWithClasses(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        return convertToDetailDto(student);
    }

    @Override
    public StudentDto createStudent(Student student) {
        Student savedStudent = studentRepository.save(student);
        return convertToDto(savedStudent);
    }

    @Override
    public StudentDto updateStudent(Integer studentId, Student student) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPhone(student.getPhone());

        Student updatedStudent = studentRepository.save(existingStudent);
        return convertToDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Integer studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Student not found with id: " + studentId);
        }
        studentRepository.deleteById(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDto> searchStudents(String keyword, Pageable pageable) {
        return studentRepository.findByKeyword(keyword, pageable).map(this::convertToDto);
    }

    // Helper methods to convert entities to DTOs
    private StudentDto convertToDto(Student student) {
        return new StudentDto(
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getPhone(),
                student.getCreatedAt()
        );
    }

    private StudentDetailDto convertToDetailDto(Student student) {
        StudentDetailDto dto = new StudentDetailDto();
        dto.setStudentId(student.getStudentId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setCreatedAt(student.getCreatedAt());

        if (student.getClassStudents() != null) {
            dto.setClasses(
                    student.getClassStudents().stream()
                            .map(cs -> new StudentClassDto(
                                    cs.getClassEntity().getClassId(),
                                    cs.getClassEntity().getTopic(),
                                    cs.getClassEntity().getStartTime(),
                                    cs.getClassEntity().getEndTime(),
                                    new TeacherDto(
                                            cs.getClassEntity().getTeacher().getTeacherId(),
                                            cs.getClassEntity().getTeacher().getName(),
                                            cs.getClassEntity().getTeacher().getEmail(),
                                            cs.getClassEntity().getTeacher().getSpecialization()
                                    )
                            ))
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}