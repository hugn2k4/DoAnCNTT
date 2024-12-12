package com.laptop.config;


import com.laptop.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

public class EmailProducer {
    private static final String QUEUE_NAME = "promotion_queue";
    private static final String HOST = "cougar.rmq.cloudamqp.com";

    public void sendToQueue(String email, String subject, String body,String nameImg) {
        try {
            try (Connection connection = RabbitMQUtils.createConnection();
                 Channel channel = RabbitMQUtils.createChannel(connection)) {
                // Khai báo hàng đợi
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);

                // Tạo thông điệp
                String message = String.format("%s|%s|%s|%s", email, subject, body, nameImg);

                // Đẩy thông điệp vào hàng đợi
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
