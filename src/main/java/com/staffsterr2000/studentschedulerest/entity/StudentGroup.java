package com.staffsterr2000.studentschedulerest.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sgroup")
@Data
@NoArgsConstructor
public class StudentGroup {

    /**
     * Name for the DB sequence
     */
    private static final String SEQUENCE_NAME = "sgroup_sequence";


    /**
     * StudentGroup's ID
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
    private String name;

    @OneToMany(mappedBy = "studentGroup")
    private List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "sgroup_course",
            joinColumns = @JoinColumn(
                    name = "sgroup_id",
                    foreignKey = @ForeignKey(name = "FK_COURSE_SGROUP")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "course_id",
                    foreignKey = @ForeignKey(name = "FK_SGROUP_COURSE")
            )
    )
    private List<Course> courses;

}
