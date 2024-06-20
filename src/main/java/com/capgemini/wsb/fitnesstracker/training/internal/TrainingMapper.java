package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

public class TrainingMapper {
    public static Training mapowanieTreningu(Training existingTraining, Training updatedTraining) {
        existingTraining.setDistance(updatedTraining.getDistance());
        existingTraining.setAverageSpeed(updatedTraining.getAverageSpeed());
        existingTraining.setActivityType(updatedTraining.getActivityType());
        return existingTraining;
    }
}
