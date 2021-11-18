package com.staffsterr2000.studentschedulerest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Lecture {


    /**
     * Name for the DB sequence
     */
    private static final String SEQUENCE_NAME = "lecture_sequence";


    /**
     * Lecture's ID
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

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "course_id",
            foreignKey = @ForeignKey(name="FK_COURSE")
    )
    private Course course;

    @NotNull
    private LocalDate localDate;

}
