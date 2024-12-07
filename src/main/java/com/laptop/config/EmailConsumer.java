package com.laptop.config;

import com.laptop.utils.MailUtils;
import com.laptop.utils.RabbitMQUtils;
import com.rabbitmq.client.*;
import jakarta.mail.MessagingException;

public class EmailConsumer {
    private final static String QUEUE_NAME = "promotion_queue";
    private static final String HOST = "cougar.rmq.cloudamqp.com";
    private static Connection connection;
    private static Channel channel;

    public static void consumeEmails() throws Exception {
        // Tạo kết nối đến RabbitMQ
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost(HOST);
//        factory.setPort(5672);
//        factory.setUsername("bpivmugb");
//        factory.setPassword("laqf07nrbSLZSWzfm0ocENIzhHEUfssB");  // Thay thế bằng mật khẩu thực tế
//        factory.setVirtualHost("bpivmugb");
        Connection connection = RabbitMQUtils.createConnection();
//        channel = connection.createChannel();
        Channel channel = RabbitMQUtils.createChannel(connection);
        // Khai báo hàng đợi
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        System.out.println("Waiting for messages. To exit press CTRL+C");

        // Định nghĩa phương thức xử lý thông điệp
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            try {
                System.out.println("Received message: " + message);

                // Phân tích message
                if (message.length() > 1) {
                    String[] messageParts = message.split("\\|");
                    if (messageParts.length < 4) {
                        throw new IllegalArgumentException("Invalid message format");
                    }

                    String email = messageParts[0];
                    String subject = messageParts[1];
                    String body = messageParts[2];
                    String img = messageParts[3];

                    // Gửi email
                    String context = MailUtils.buildDetailPromotion(email, subject, body, img);
                    MailUtils.sendMail(email, subject, context);

                    System.out.println("Email sent to: " + email);
                } else {
                    System.out.println("Insufficient data in message.");
                }

                // Xác nhận thông báo đã xử lý thành công
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            } catch (Exception e) {
                System.err.println("Error processing message: " + e.getMessage());
                // Không xác nhận và để RabbitMQ xử lý lại
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
            }
        };

        // Tiếp nhận và xử lý thông điệp với autoAck = false
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {});
    }

    // Hàm để đóng kết nối khi cần
    public static void close() throws Exception {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
    }
}
