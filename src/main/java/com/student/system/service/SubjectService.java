package com.student.system.service;

import com.student.system.entity.Subject;
import com.student.system.exception.SubjectException;
import com.student.system.repository.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    public List<Subject> listAllSubjects() throws SubjectException {
        List<Subject> subjectList = null;
        try {
            subjectList = subjectRepository.findAll();
        } catch (Exception exp) {
            throw new SubjectException(exp.getMessage());
        }
        return subjectList;
    }

    public Subject addSubject(String subjectName) throws SubjectException {
        Subject subject = null;
        try {
            if (Objects.nonNull(subjectName) && !"".equals(subjectName)) {
                subject = new Subject();
                subject.setSubjectName(subjectName);
                subject = subjectRepository.save(subject);
            }
        } catch (Exception exception) {
            log.error("Error while adding the subject : {}", exception);
            throw new SubjectException("Error while adding the subject");
        }
        return subject;
    }

    public void deleteSubject(String subjectName) throws SubjectException {
        try {
            if (Objects.nonNull(subjectName) && !"".equals(subjectName)) {
                Subject subject = subjectRepository.findBySubjectName(subjectName);
                if (Objects.isNull(subject)) {
                    log.error("No subject found for the Name : {}", subjectName);
                    throw new SubjectException("No subject found for the Name :" + subjectName);
                } else {
                    subjectRepository.delete(subject);
                }
            } else {
                throw new SubjectException("NULL Values in deleteSubject");
            }
        } catch (Exception exception) {
            log.error("Error while deleting the subject :{}", exception);
            throw new SubjectException(exception.getMessage());
        }
    }

}
