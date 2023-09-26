package com.student.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name="subject")
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    @JsonProperty("subject_name")
    @Column(name = "subject_name")
    String subjectName;

    @JsonIgnore
    @ManyToMany(mappedBy = "subjects",fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();
}
