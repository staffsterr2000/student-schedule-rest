package com.staffsterr2000.studentschedulerest.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "sgroup_id",
            foreignKey = @ForeignKey(name = "FK_SGROUP")
    )
    private StudentGroup studentGroup;

}
