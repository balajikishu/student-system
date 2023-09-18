package com.student.system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity(name="subject")
public class Subject implements Serializable {

    @Id
    @Column(name = "id")
    Integer id;

    @Column(name = "subject_name")
    String subjectName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;


}
