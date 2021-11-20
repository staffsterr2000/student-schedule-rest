package com.staffsterr2000.studentschedulerest.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
@Data
@NoArgsConstructor
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
    private List<StudentGroup> studentGroups;

}
