package com.staffsterr2000.studentschedulerest.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Student {


    /**
     * Name for the DB sequence
     */
    private static final String SEQUENCE_NAME = "student_sequence";


    /**
     * Student's ID
     */
    @Id
    @SequenceGenerator(
            name = SEQUENCE_NAME,
            sequenceName = SEQUENCE_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = SEQUENCE_NAME
    )
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "FK_COURSE_STUDENT")),
            inverseJoinColumns = @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "FK_STUDENT_COURSE"))
    )
    private List<Course> courses;

}
