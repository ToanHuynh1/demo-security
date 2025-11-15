package com.demo_security.demo_security.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@EnableAsync
public class DemoScheduler {
    private static final Logger logger = LoggerFactory.getLogger(DemoScheduler.class);

    // Chạy mỗi 10 giây
    @Scheduled(fixedRate = 10000)
    public void scheduledTask() {
        logger.info("[SCHEDULED] Task chạy mỗi 10 giây: {}", System.currentTimeMillis());
    }

    // Chạy bất đồng bộ (không block thread chính)
    @Async
    public void asyncTask() {
        logger.info("[ASYNC] Task bắt đầu: {}", Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("[ASYNC] Task kết thúc: {}", Thread.currentThread().getName());
    }
}
