package com.staffsterr2000.studentschedulerest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"students"})
@EqualsAndHashCode(exclude = {"students"})
public class Course {

    public enum Subject {
        HISTORY,
        MATH,
        ENGLISH
    }

    /**
     * Name for the DB sequence
     */
    private static final String SEQUENCE_NAME = "course_sequence";



    /**
     * Course's ID
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
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @NotNull
    private String teacherFullName;

    @OneToMany(mappedBy = "course")
    private List<Lecture> lectures;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

}
