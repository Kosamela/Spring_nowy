package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public Integer averageDistancePerTraining(Long id) {
        Statistics statistics = statisticsRepository.getStatisticsById(id).orElseThrow(EntityNotFoundException::new);
        Integer distance = (int) statistics.getTotalDistance();
        Integer totalTrainings = statistics.getTotalTrainings();
        if (distance == null || totalTrainings == null || totalTrainings == 0) {
            return 0;
        }
        else {
            return distance / totalTrainings;
        }
    }
}

