package com.laptop.config;

import com.laptop.service.SchedulerService;
import org.quartz.SchedulerException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
public class AppStartupListener implements ServletContextListener {
    private SchedulerService quartzScheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        quartzScheduler = new SchedulerService();
        try {
            quartzScheduler.startScheduler();

            // Khởi chạy EmailConsumer
            Thread consumerThread = new Thread(() -> {
                EmailConsumer consumer = new EmailConsumer();
                try {
                    consumer.consumeEmails(); // Lắng nghe queue
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            consumerThread.start();
            System.out.println("EmailConsumer started.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Dừng Quartz Scheduler khi ứng dụng dừng
        if (quartzScheduler != null) {
            System.out.println(" [*] Stopping Quartz Scheduler...");
        }
    }
}
