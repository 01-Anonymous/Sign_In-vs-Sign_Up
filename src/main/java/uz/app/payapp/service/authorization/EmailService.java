package uz.app.payapp.service.authorization;

import lombok.SneakyThrows;
import uz.app.payapp.db.ConfirmationCodeDAO;
import uz.app.payapp.util.Sender;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;


import java.util.Properties;
import java.util.Random;

public class EmailService {
    Properties properties = new Properties();

    @SneakyThrows
    public void sendEmail(String email, String code) {
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Sender.email, Sender.password);
            }
        };
        Session session = Session.getInstance(properties, authenticator);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Sender.email));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("Tasdiqlash kodi");
        message.setText("Kod: " + code);

        Transport.send(message);
    }


    public static String getConfirmationCode(String email) {
        ConfirmationCodeDAO.deleteAllConfirmationCodes(email);

        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++)
            sb.append(new Random().nextInt(10));

        String code = sb.toString();

        Thread thread = new Thread(() -> {
            new EmailService().sendEmail(email, code);
        });
        thread.start();

        ConfirmationCodeDAO.saveConfirmationCode(email, code);
        return code;
    }
}
