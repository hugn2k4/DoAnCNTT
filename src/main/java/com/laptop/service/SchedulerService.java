package com.laptop.service;

import com.laptop.config.PromotionJob;
import org.quartz.JobDetail;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerService {
    public void startScheduler() throws SchedulerException {
        // Khởi tạo scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // Khởi tạo JobDetail
        JobDetail job = JobBuilder.newJob(PromotionJob.class)
                .withIdentity("promotionJob", "group1")
                .build();

        // Tạo trigger để job chạy mỗi ngày
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("promotionTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(24)  // Chạy mỗi 24 giờ
                        .repeatForever())
                .build();


        // Gắn trigger vào job
        scheduler.scheduleJob(job, trigger);

        // Start scheduler
        scheduler.start();
    }
}
