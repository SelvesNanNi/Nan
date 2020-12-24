package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Selves
 * @Date 2020/12/17
 */
public class JMail {
    static String host = "smtp.gmail.com";
    static String ip = "74.125.137.108";
    static int port = 587;
    static String fromUser = "selves3moa@gmail.com";
    static String fromUserEmailPassword = "google121610";

    public static void main(String[] args) {
        send("nizhengnan@lizhi.fm", "Tiya", "官方验证码");
    }

    public static void send(String To_mail, String Mail_title, String Mail_text) {

        Properties props = new Properties();
        props.put("mail.smtp.host", ip);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", port);
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromUser, fromUserEmailPassword);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To_mail));
            message.setSubject(Mail_title);
            message.setText(Mail_text);
            Transport transport = session.getTransport("smtp");
            transport.connect(ip, port, fromUser, fromUserEmailPassword);
            Transport.send(message);
            System.out.println(To_mail + "  " + Mail_title + " " + "寄送email結束.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}