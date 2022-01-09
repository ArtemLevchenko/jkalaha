package com.artbender.core.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Persistence entity for Statistics
 *
 * @author Artsiom Leuchanka
 */

@Data
@Entity
@Table(name = "halloffame")
public class HallOfFame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "winner_name")
    private String winnerName;
    @Column(name = "date")
    private Date dateOfVictory;

}
