package com.laptop.utils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class RabbitMQUtils {
    private static final String QUEUE_NAME = "promotion_queue";
    private static final String HOST = "cougar.rmq.cloudamqp.com";
    private static final String USERNAME = "bpivmugb";
    private static final String PASSWORD = "laqf07nrbSLZSWzfm0ocENIzhHEUfssB";
    private static final String VHOST = "bpivmugb";
    public static Connection  createConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(5672);
        factory.setUsername(USERNAME);  // Cập nhật tên người dùng
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VHOST);// Cập nhật mật khẩu
        factory.setAutomaticRecoveryEnabled(true);
        factory.setTopologyRecoveryEnabled(true);
        //Connection connection = factory.newConnection();
        return factory.newConnection();
    }

    public static Channel createChannel(Connection connection) throws Exception {
        return connection.createChannel();
    }

    public static void declareQueue(Channel channel) throws Exception {
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    }

    public static String getQueueName() {
        return QUEUE_NAME;
    }
}
