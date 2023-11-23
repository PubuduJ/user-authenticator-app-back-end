package lk.pubudu.app.util;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lk.pubudu.app.dto.UserDTO;
import lk.pubudu.app.exception.NotFoundException;
import lk.pubudu.app.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EMailSender {

    private final Environment env;

    private final JavaMailSender javaMailSender;

    public void sendWelcomeMail(UserDTO userDTO, String password) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(userDTO.getEmail());
            helper.setSubject("Welcome to User Authenticator Application");
            helper.setFrom(new InternetAddress(env.getProperty("spring.mail.username"), "Admin"));

            helper.setText("<html><body style='background-color:#E5E9F1'>"
                    + "<table>"
                    + "<tr><td style='padding:10px'>Welcome to User Authenticator Application</td></tr>"
                    + "<tr><td style='padding:10px'>You can login to the system using following credentials</td></tr>"
                    + "<tr><td style='padding:10px'>System URL - <a href='http://localhost:3000/sign-in'>User Authenticator App</a></td></tr>"
                    + "<tr><td style='padding:10px'>Temporary password - " + password + "</td></tr>"
                    + "</table>"
                    + "</body></html>", true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new NotFoundException("Invalid email id");
        }
    }

    public void sendResetPasswordMail(User user, String password) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("User Authenticator App - Password Reset");
            helper.setFrom(new InternetAddress(env.getProperty("spring.mail.username"), "Admin"));

            helper.setText("<html><body style='background-color:#E5E9F1'>"
                    + "<table>"
                    + "<tr><td style='padding:10px'>Your password reset request was success</td></tr>"
                    + "<tr><td style='padding:10px'>You can login to the system using following credentials</td></tr>"
                    + "<tr><td style='padding:10px'>System URL - <a href='http://localhost:3000/sign-in'>User Authenticator App</a></td></tr>"
                    + "<tr><td style='padding:10px'>Temporary password - " + password + "</td></tr>"
                    + "</table>"
                    + "</body></html>", true);
            javaMailSender.send(message);

        } catch (Exception e) {
            throw new NotFoundException("Invalid email id");
        }
    }
}