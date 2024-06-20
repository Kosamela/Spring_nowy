package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import java.util.List;

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
    private String weekRaport(User user){
        List<Training> trainingList = trainingRepository.findByUserId(user.getId());
        Training lastTraining = trainingList.get(0);
        long trainingCount = trainingList.size();
        return "Your weekly dose of trainings: " + trainingCount+ "\n" +
                "Your last activity was: " + lastTraining.getActivityType().toString();
    }
    private void sendRaport(String email, String raport){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Weekly training raport");
        message.setText(raport);
        mailSender.send(message);
    }
    @Scheduled(cron = "0 0 0 * * MON")
    public void generateTrainingRaport() {
        //TODO
    }
}
