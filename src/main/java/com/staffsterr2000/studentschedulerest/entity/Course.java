package com.staffsterr2000.studentschedulerest.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @NotNull
    private String teacherFullName;

    @OneToMany(mappedBy = "course")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Lecture> lectures;

    @ManyToMany(mappedBy = "courses")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<StudentGroup> studentGroups;

}
