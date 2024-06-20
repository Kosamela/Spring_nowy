package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnableScheduling
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


    private static final Logger logger = LoggerFactory.getLogger(TrainingRaportService.class);

    private void prepRaport(String email, String raport) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setSubject("Weekly training raport");
            helper.setText(raport);

            logger.info("Preparing to send email to {}", email);
            mailSender.send(message);
            logger.info("Email sent to {}", email);
        } catch (MessagingException e) {
            logger.error("Error while sending email: ", e);
        }
    }
    @Scheduled(fixedDelay = 5000)
    public void generateAndSendTrainingRaport() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            System.out.println("No users found in the database.");
            return;
        }
        System.out.println("tutaj");
        for(User user : users){
            System.out.println("tutaj1");
            String raport = weekRaport(user);
            prepRaport(user.getEmail(), raport);
    }
}}