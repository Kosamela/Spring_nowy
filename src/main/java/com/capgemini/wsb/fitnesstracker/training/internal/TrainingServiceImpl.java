package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public List<Training> getTrainingsByUser(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    public List<Training> getCompletedTrainingsAfter(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return trainingRepository.findByEndTimeAfter(date);
    }

    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    public Training updateTraining(Long trainingId, Training updatedTraining) {
        Optional<Training> existingTrainingOpt = trainingRepository.findById(trainingId);
        if (existingTrainingOpt.isPresent()) {
            Training existingTraining = existingTrainingOpt.get();
            existingTraining.setDistance(updatedTraining.getDistance());
            existingTraining.setAverageSpeed(updatedTraining.getAverageSpeed());
            existingTraining.setActivityType(updatedTraining.getActivityType());
            return trainingRepository.save(existingTraining);
        } else {
            throw new EntityNotFoundException("Training not found");
        }
    }

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }
}
