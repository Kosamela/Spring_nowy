package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TrainingRaportService {
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final JavaMailSender mailSender;
    public TrainingRaportService(UserRepository userRepository, TrainingRepository trainingRepository, JavaMailSender mailSender){
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.mailSender = mailSender;
    }
    @Scheduled(cron = "0 0 0 * * MON")
    public void generateTrainingRaport() {
        //TODO
    }
}
