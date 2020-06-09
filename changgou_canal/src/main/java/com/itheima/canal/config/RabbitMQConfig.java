package com.itheima.canal.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String AD_UPDATE_QUEUE = "ad_update_queue";

    @Bean
    public Queue queue() {
        return new Queue(AD_UPDATE_QUEUE);
    }

}
