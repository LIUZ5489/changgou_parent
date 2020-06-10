package com.itheima.canal.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String AD_UPDATE_QUEUE = "ad_update_queue";
    //定义队列名称(更新上架商品)
    private static final String SEARCH_ADD_QUEUE = "search_add_queue";

    //交换机名称(更新上架商品)
    private static final String GOODS_UP_EXCHANGE = "goods_up_exchange";

    //声明队列
    @Bean
    public Queue ad_update_queue() {
        return new Queue(AD_UPDATE_QUEUE);
    }

    //声明队列
    @Bean(SEARCH_ADD_QUEUE)
    public Queue search_add_queue() {
        return new Queue(SEARCH_ADD_QUEUE);
    }

    //声明交换机
    @Bean(GOODS_UP_EXCHANGE)
    public Exchange goods_up_exchange() {
        return ExchangeBuilder.fanoutExchange(GOODS_UP_EXCHANGE).build();
    }

    //队列与交换机绑定
    @Bean
    public Binding goods_up_exchange_search_add_queue(@Qualifier(SEARCH_ADD_QUEUE) Queue queue, @Qualifier(GOODS_UP_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
}
