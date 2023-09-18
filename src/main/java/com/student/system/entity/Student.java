package com.student.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name="student")
public class Student implements Serializable {

    @JsonProperty("usn")
    @Id
    @Column(name = "usn")
    private String usn; // Universal Serial Number

    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @JsonProperty("age")
    @Column(name = "age")
    private Integer age;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "student_subject",
    joinColumns = @JoinColumn(name = "usn"),
    inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Subject> subjects = new HashSet<>();

}
