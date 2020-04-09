package HomeLibraryPackage;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**Класс, предоставляющий методы для отправки емэйл-сообщений*/
public class EmailSender {

    /**Статический метод для отправки емэйл-сообщений*/
    public static void send(String receiverAddress, String subject, String text) {
        final String username = "kentavr220291@mail.ru";
        final String password = "Nagibator220291";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverAddress));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            System.out.println("Message send to "+receiverAddress);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
