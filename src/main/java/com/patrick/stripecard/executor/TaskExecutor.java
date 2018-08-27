package com.patrick.stripecard.executor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@PropertySource("file:${CONFIG_PATH}/taskExecutor.properties")
public class TaskExecutor {

    @Value("${patrick.co.ke.stripe.taskExecutor.timeout}")
    private int timeout;


    @Value("${patrick.co.ke.stripe.taskExecutor.corePool}")
    private int corePool;

    @Value("${patrick.co.ke.stripe.taskExecutor.maxPool}")
    private int maxPool;

    @Value("${patrick.queue.capacity}")
    private int capacity;


    @Bean
    @Qualifier("stripeExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePool);
        threadPoolTaskExecutor.setMaxPoolSize(maxPool);
        threadPoolTaskExecutor.setQueueCapacity(capacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(timeout);

        return threadPoolTaskExecutor;
    }
}
