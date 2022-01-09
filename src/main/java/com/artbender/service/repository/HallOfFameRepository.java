package com.artbender.service.repository;

import com.artbender.core.model.HallOfFame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for work with HallOfFame entity
 *
 * @author Artsiom Leuchanka
 */
@Repository
public interface HallOfFameRepository extends JpaRepository<HallOfFame, Long> {
}
