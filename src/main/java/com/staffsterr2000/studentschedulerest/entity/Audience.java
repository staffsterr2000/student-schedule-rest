package com.staffsterr2000.studentschedulerest.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Audience {


    /**
     * Name for the DB sequence
     */
    private static final String SEQUENCE_NAME = "audience_sequence";


    /**
     * Audience's ID
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
    private Integer roomNumber;

}
