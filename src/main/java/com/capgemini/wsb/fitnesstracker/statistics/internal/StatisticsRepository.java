package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Integer totalTime();
    Integer getDistance();
    Optional <Statistics> getStatisticsById(Long statisticsId);
    Integer getTotalTrainings();

}