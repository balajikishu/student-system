package com.student.system.controller;

import com.student.system.exception.SubjectException;
import com.student.system.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping("/all")
    ResponseEntity listAllSubjects() {
        try {
            return new ResponseEntity(subjectService.listAllSubjects(), HttpStatus.OK);
        } catch (Exception | SubjectException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    ResponseEntity addSubject(@RequestParam("subject_name") String subjectName) {
        try {
            return new ResponseEntity(subjectService.addSubject(subjectName), HttpStatus.CREATED);
        } catch (Exception | SubjectException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    ResponseEntity deleteSubject(@RequestParam("subject_name") String subjectName) {
        try {
            subjectService.deleteSubject(subjectName);
            return new ResponseEntity("Resource Deleted", HttpStatus.OK);
        } catch (Exception | SubjectException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
