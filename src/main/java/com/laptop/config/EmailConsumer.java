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
        Connection connection = RabbitMQUtils.createConnection();
        Channel channel = RabbitMQUtils.createChannel(connection);
        // Khai báo hàng đợi
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // Định nghĩa phương thức xử lý thông điệp
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            try {
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
