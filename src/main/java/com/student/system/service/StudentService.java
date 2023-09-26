package com.student.system.service;

import com.student.system.entity.Student;
import com.student.system.entity.Subject;
import com.student.system.exception.StudentException;
import com.student.system.exception.SubjectException;
import com.student.system.repository.StudentRepository;
import com.student.system.repository.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

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
                Optional<Student> optionalStudent = studentRepository.findById(studentInfo.getUsn());
                if (optionalStudent.isPresent()) {
                    throw new StudentException("The USN: " + studentInfo.getUsn() + "  is already registered, Kindly use another one.");
                } else {
                    student = new Student();
                    student.setUsn(studentInfo.getUsn());
                    student.setAge(studentInfo.getAge());
                    student.setFirstName(studentInfo.getFirstName());
                    student.setLastName(studentInfo.getLastName());
                    student = studentRepository.save(student);
                }
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


    public void addSubjectsForStudent(Integer[] subjectList, String usn) throws SubjectException {
        try {
            if (Objects.nonNull(subjectList) && subjectList.length > 0 && Objects.nonNull(usn)) {

                    Student student = studentRepository.findById(usn).orElseThrow(()
                            -> new SubjectException("No Student of USN : " + usn + " to map the Subjects"));
                    List<Subject> subjects = subjectRepository.findAllById(List.of(subjectList));
                    student.getSubjects().addAll(subjects);

                    studentRepository.save(student);
            }
        } catch (Exception exception) {
            log.error("Error while mapping the subject :{}", exception);
            throw new SubjectException(exception.getMessage());
        }
    }


    public void removeSubjectsForStudent(Integer[] subjectList, String usn) throws SubjectException {
        try {
            if (Objects.nonNull(subjectList) && subjectList.length > 0 && Objects.nonNull(usn)) {

                Student student = studentRepository.findById(usn).orElseThrow(()
                        -> new SubjectException("No Student of USN : " + usn + " to un-map the Subjects"));
                List<Subject> subjects = subjectRepository.findAllById(List.of(subjectList));
                student.getSubjects().removeAll(subjects);

                studentRepository.save(student);

            }
        } catch (Exception exception) {
            log.error("Error while unmapping the subject :{}", exception);
            throw new SubjectException(exception.getMessage());
        }
    }

    public Map<String, List<String>> getStudentNameAndSubjectsMappedByUSN(String usn) throws SubjectException {
        try {
            Map<String, List<String>> mapInfo = new HashMap<>();
            if (Objects.nonNull(usn)) {
                Student student = studentRepository.findById(usn).orElseThrow(()
                        -> new SubjectException("No Student of USN : " + usn + " to map the Subjects"));
                Set<Subject> subjects = student.getSubjects();
                List<String> subjectList = subjects.stream().map(Subject::getSubjectName)
                        .collect(Collectors.toCollection(ArrayList::new));
                System.out.printf("SUBJECTS LIST :" + subjectList.toString());
                mapInfo.put(student.getFirstName() + " " + student.getLastName(), subjectList);
            }
            return mapInfo;
        } catch (Exception exception) {
            log.error("Error while retrieving the subject's for Student :{}", exception);
            throw new SubjectException(exception.getMessage());
        }
    }

}
