package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class SendMail {
    private int OTP;

    public int sendMailToUser(String userEmail) {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "nptelvignesh@gmail.com"; //fill it
        final String password = "vignesh@9655931074";
        OTP = new Random().nextInt(999999);
        //System.err.println("Your OTP " + OTP);
        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(userEmail, false));
            msg.setSubject("OTP Verification for Application");
            msg.setText("Your Account verification otp code is here, Your OTP code is " + OTP);
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
            return OTP;
        } catch (MessagingException e) {
            System.out.println("error cause: " + e);
        }
        return OTP;
    }

    public boolean verifyOTP() {
        System.out.println("Enter OTP =");
        return new Scanner(System.in).nextInt() == OTP;
    }
}
