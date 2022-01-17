package com.ameed.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRabbitConfig {
    public static final String EXCHANGE_NAME = "str-exchange";
    public static final String QUEUE_NAME1 = "str-len";
    public static final String BINDING_KEY1 = "str-key";
    public static final String QUEUE_NAME2 = "str-uppercase";
    public static final String BINDING_KEY2 = "str-uppercase-key";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue1() {
        return new Queue(QUEUE_NAME1);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1())
                .to(directExchange())
                .with(BINDING_KEY1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE_NAME2);
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2())
                .to(directExchange())
                .with(BINDING_KEY2);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer>
    prefetchOne(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        container.setMessageConverter(messageConverter());
        container.setPrefetchCount(1);
        container.setConcurrentConsumers(5);
        return container;
    }
}
