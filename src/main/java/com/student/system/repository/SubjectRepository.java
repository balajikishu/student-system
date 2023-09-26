package com.student.system.repository;

import com.student.system.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    Subject findBySubjectName(String subjectName);
}
