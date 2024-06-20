package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/trainings")
public class TrainingController {

    private final TrainingServiceImpl trainingService;

    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        Training createdTraining = trainingService.createTraining(training);
        return new ResponseEntity<>(createdTraining, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Training>> getAllTrainings() {
        List<Training> trainings = trainingService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Training>> getTrainingsByUser(@PathVariable Long userId) {
        List<Training> trainings = trainingService.getTrainingsByUser(userId);
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/finished/{date}")
    public ResponseEntity<List<Training>> getCompletedTrainingsAfter(@PathVariable String date) {
        List<Training> trainings = trainingService.getCompletedTrainingsAfter(date);
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/activityType/{activityType}")
    public ResponseEntity<List<Training>> getTrainingsByActivityType(@PathVariable ActivityType activityType) {
        List<Training> trainings = trainingService.getTrainingsByActivityType(activityType);
        return ResponseEntity.ok(trainings);
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<Training> updateTraining(
            @PathVariable Long trainingId, @RequestBody Training updatedTraining) {
        Training training = trainingService.updateTraining(trainingId, updatedTraining);
        return ResponseEntity.ok(training);
    }
}
