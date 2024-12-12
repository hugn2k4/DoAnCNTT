package com.laptop.config;

import com.laptop.models.Promotion;
import com.laptop.models.User;
import com.laptop.service.PromotionService;
import com.laptop.service.UserService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import jakarta.mail.MessagingException;
import org.quartz.*;
import com.laptop.utils.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PromotionJob implements Job {


    private UserService userService;
    private PromotionService promotionService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("PromotionJob is running...");
        userService = new UserService();
        promotionService = new PromotionService();
        EmailProducer emailProducer = new EmailProducer();

        // Khởi tạo ExecutorService để quản lý các luồng
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            Connection connection = RabbitMQUtils.createConnection();
            Channel channel = RabbitMQUtils.createChannel(connection);
           // Channel channel = RabbitMQUtils.getChannel();
            RabbitMQUtils.declareQueue(channel);

            List<Promotion> promotions = promotionService.getAll();

            for (Promotion promotion : promotions) {
                LocalDateTime today = LocalDateTime.now(); // Ngày hiện tại dưới dạng LocalDateTime
                LocalDateTime startDateTime = promotion.getStartsAt(); // Ngày bắt đầu (LocalDateTime)
                LocalDateTime endDateTime = promotion.getEndsAt();

                // Kiểm tra nếu ngày bắt đầu chương trình là hôm nay
                if (!today.isBefore(startDateTime) && !today.isAfter(endDateTime)) {
                    // Lấy danh sách tất cả email của người dùng đã đăng ký
                    List<String> customerEmails = userService.getAllEmail();
                    String subject = "Chương trình khuyến mãi: " + promotion.getName();
                    String body = promotion.getDescription();
                    String nameImg = promotion.getImageName();

                    executorService.submit(() -> {
                        for (String email : customerEmails) {
                            emailProducer.sendToQueue(email, subject, body, nameImg);
                        }
                    });
                }
                else {
                    System.out.println("Skipping promotion: " + promotion.getName());
                }
            }
            channel.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // Đảm bảo rằng ExecutorService được đóng lại khi hoàn thành
            executorService.shutdown();
        }
        // Lấy tất cả các chương trình khuyến mãi đang hoạt động
    }

    private String todayDate() {
        // Trả về ngày hiện tại dưới dạng chuỗi
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    }
}
