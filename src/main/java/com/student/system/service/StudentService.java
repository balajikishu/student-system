package com.student.system.service;

import com.student.system.entity.Student;
import com.student.system.exception.StudentException;
import com.student.system.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> listAllStudents() throws StudentException {
        List<Student> studentList = null;
        try {
            studentList = studentRepository.findAll();
        } catch (Exception exp) {
            throw new StudentException(exp.getMessage());
        }
        return studentList;
    }

    public Student getStudentByUSN(String usn) throws StudentException {
        Student student = null;
        try {
            if (Objects.nonNull(usn)) {
                Optional<Student> optionalStudent = studentRepository.findById(usn);
                if (optionalStudent.isPresent()) {
                    student = optionalStudent.get();
                } else {
                    log.error("No Student available for the USN : {}", usn);
                    new StudentException("No Student available for the USN :" + usn);
                }
            }
        } catch (Exception exp) {
            throw new StudentException(exp.getMessage());
        }
        return student;
    }

    public Student addStudent(Student studentInfo) throws StudentException {
        Student student = null;
        try {
            if (Objects.nonNull(studentInfo)) {
                student = new Student();
                student.setUsn(studentInfo.getUsn());
                student.setAge(studentInfo.getAge());
                student.setFirstName(studentInfo.getFirstName());
                student.setLastName(studentInfo.getLastName());
                student = studentRepository.save(student);
            }
        } catch (Exception exp) {
            log.error("Error while saving the student: {}", exp);
            new StudentException("Error while saving the student");
        }
        return student;
    }

    public void deleteStudentByUSN(String usn) throws StudentException {
        try {
            if (Objects.nonNull(usn)) {
                Optional<Student> optionalStudent = studentRepository.findById(usn);
                if (optionalStudent.isPresent()) {
                    Student student = optionalStudent.get();
                    studentRepository.delete(student);
                } else {
                    log.error("No Student available for the USN : {}", usn);
                    new StudentException("No Student available for the USN :" + usn);
                }
            }
        } catch (Exception exp) {
            log.error("Error while deleting a student for USN :{} : {}", usn, exp);
            new StudentException("Error while deleting a student for USN:" + usn);
        }
    }

    public Student updateStudent(Student studentInfo) throws StudentException {
        Student student = null;
        try {
            if (Objects.nonNull(studentInfo)) {
                Optional<Student> optionalStudent = studentRepository.findById(studentInfo.getUsn());
                if (optionalStudent.isPresent()) {
                    student = optionalStudent.get();
                    student.setUsn(studentInfo.getUsn());
                    student.setAge(studentInfo.getAge());
                    student.setFirstName(studentInfo.getFirstName());
                    student.setLastName(studentInfo.getLastName());
                    student = studentRepository.save(student);
                }
            }
        } catch (Exception exp) {
            log.error("Error while updating the student: {}", exp);
            new StudentException("Error while updating the student");
        }
        return student;
    }


}
