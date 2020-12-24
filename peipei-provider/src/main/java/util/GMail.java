package util;

import jodd.mail.Email;
import jodd.mail.MailServer;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;

/**
 * @author Selves
 * @Date 2020/12/17
 */
public class GMail {
    public static void main(String[] args) {
        SmtpServer smtpServer = MailServer.create()
                .ssl(true)
                .host("smtp.gmail.com")
                .auth("selves3moa@gmail.com", "google121610")
                .buildSmtpMailServer();

        Email email = Email.create()
                .from("selves3moa@gmail.com")
                .to("nizhengnan@lizhi.fm")
                .subject("hello")
                .textMessage("Hello world!");

        SendMailSession session = smtpServer.createSession();
        session.open();
        session.sendMail(email);
        session.close();
    }
}
