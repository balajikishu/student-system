package com.student.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.system.entity.Student;
import com.student.system.exception.StudentException;
import com.student.system.exception.SubjectException;
import com.student.system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/all")
    ResponseEntity listAllStudents() {
        try {
            return new ResponseEntity(studentService.listAllStudents(), HttpStatus.OK);
        } catch (Exception | StudentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    ResponseEntity addStudent(@RequestBody Student student) {
        try {
            return new ResponseEntity(studentService.addStudent(student), HttpStatus.CREATED);
        } catch (Exception | StudentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    ResponseEntity updateStudent(@RequestBody Student student) {
        try {
            return new ResponseEntity(studentService.updateStudent(student), HttpStatus.OK);
        } catch (Exception | StudentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{usn}")
    ResponseEntity deleteStudent(@PathVariable("usn") String usn) {
        try {
            studentService.deleteStudentByUSN(usn);
            return new ResponseEntity("Resource Deleted", HttpStatus.OK);
        } catch (Exception | StudentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{usn}")
    ResponseEntity getStudentByUSN(@PathVariable("usn") String usn) {
        try {
            return new ResponseEntity(studentService.getStudentByUSN(usn), HttpStatus.OK);
        } catch (Exception | StudentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/map")
    ResponseEntity addSubjectsForStudents(@RequestBody Integer[] subjects, @RequestParam("usn") String usn) {
        try {
            studentService.addSubjectsForStudent(subjects, usn);
            return new ResponseEntity("Subjects Added", HttpStatus.OK);
        } catch (Exception | SubjectException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/un-map")
    ResponseEntity removeSubjectsForStudents(@RequestBody Integer[] subjects, @RequestParam("usn") String usn) {
        try {
            studentService.removeSubjectsForStudent(subjects, usn);
            return new ResponseEntity("Subjects Removed", HttpStatus.OK);
        } catch (Exception | SubjectException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/subjects")
    ResponseEntity getSubjectsForStudent(@RequestParam("usn") String usn) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, List<String>> listMap = studentService.getStudentNameAndSubjectsMappedByUSN(usn);
            String values = mapper.writeValueAsString(listMap);
            return new ResponseEntity(values, HttpStatus.OK);
        }catch (Exception | SubjectException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
