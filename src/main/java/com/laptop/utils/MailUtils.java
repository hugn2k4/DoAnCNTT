package com.laptop.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailUtils {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "hieunghia484@gmail.com";
    private static final String SMTP_PASSWORD = "slpb pksv ygov bxqz";


    public static void sendMail(String to, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // props.put("mail.transport.protocol", "smtps");
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });
        session.setDebug(true); // Đặt chế độ debug

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            // message.setText(content);
            message.setContent(content, "text/html; charset=utf-8"); // Hỗ trợ email HTML

            Transport.send(message);
            System.out.println("Email sent to: " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error while sending email: " + e.getMessage());
            throw e;
        }
    }

    public static String buildDetailEmail(String email) {
        return "<html>" +
                "<body>" +
                "<h1>Chào mừng bạn đến với chương trình ưu đãi của chúng tôi!</h1>" +
                "<p>Kính gửi Quý khách hàng,</p>" +
                "<p>Cảm ơn bạn đã đăng ký nhận tin khuyến mãi từ <b>LAPTOP SHOP</b>! " +
                "Chúng tôi rất hân hạnh được gửi đến bạn những thông tin mới nhất về các chương trình ưu đãi, " +
                "giảm giá, và sự kiện đặc biệt.</p>" +
                "<h3>Thông tin đăng ký của bạn:</h3>" +
                "<ul>" +
                "<li>Email: <b>" + email + "</b></li>" +
                "</ul>" +
                "<p>Chúng tôi sẽ thường xuyên cập nhật những tin tức hữu ích, và bạn sẽ luôn là người đầu tiên biết đến " +
                "các chương trình ưu đãi độc quyền.</p>" +
                "<p>Nếu bạn có bất kỳ câu hỏi nào, đừng ngần ngại liên hệ với chúng tôi qua email: " +
                "<a href=\"mailto:support@example.com\">support@example.com</a> hoặc hotline: 123-456-7890.</p>" +
                "<p>Trân trọng,</p>" +
                "<p><b>LAPTOP SHOP</b></p>" +
                "</body>" +
                "</html>";
    }

    public static String buildDetailPromotion(String email,String subject, String content, String img) throws MessagingException {
        String base64Image = img; // Assuming `img` is a Base64 string
        String encodedImage = "data:image/jpeg;base64," + base64Image;

        return "<html>" +
                "<body>" +
                "<h1>Chào mừng bạn đến với chương trình ưu đãi đặc biệt của chúng tôi!</h1>" +
                "<p>Kính gửi Quý khách hàng,</p>" +
                "<p>Cảm ơn bạn đã đăng ký nhận tin khuyến mãi từ <b>LAPTOP SHOP</b>! " +
                "Chúng tôi rất hân hạnh được gửi đến bạn những thông tin mới nhất về các chương trình ưu đãi, " +
                "giảm giá và các sự kiện đặc biệt mà bạn không thể bỏ qua.</p>" +
                "<h3>Thông tin đăng ký của bạn:</h3>" +
                "<ul>" +
                "<li>Email: <b>" + email + "</b></li>" +
                "</ul>" +
                "<h3> " + subject + "</h3>" +
                "<p>" + content + "</p>" +
                "<img src=\"" + encodedImage + "\" alt=\"Promotion Image\" style=\"width:100%;max-width:600px;\">" +
                "<p>Đừng bỏ lỡ cơ hội này! Chúng tôi sẽ liên tục cập nhật những chương trình ưu đãi " +
                "đặc biệt dành riêng cho bạn, và bạn sẽ luôn là người đầu tiên nhận được thông tin.</p>" +
                "<p>Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email: " +
                "<a href=\"mailto:support@example.com\">support@example.com</a> hoặc hotline: 123-456-7890.</p>" +
                "<p>Trân trọng,</p>" +
                "<p><b>LAPTOP SHOP</b></p>" +
                "</body>" +
                "</html>";
    }


}
