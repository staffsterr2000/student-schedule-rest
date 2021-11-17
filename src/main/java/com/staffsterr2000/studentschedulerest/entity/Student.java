package com.staffsterr2000.studentschedulerest.entity;

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

    private String firstName;

    private String lastName;

}
