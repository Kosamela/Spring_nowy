package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByUserId(Long id);

    List<Training> findByEndTimeAfter(Date endTime);

    List<Training> findByActivityType(ActivityType activityType);

    List<Training> findTrainingByEndTimeAfter(Date endTime);
}
