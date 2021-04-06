package jee3060.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jee3060.consumer.service.Consumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration


public class ConsumerConfiguration {
    @Value("${ramq.rabbit.queue}")
    private String rabbitQueue;

    @Value("${ramq.rabbit.exchange}")
    private String rabbitExchange;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Queue queue(){
        return new Queue(rabbitQueue, false);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(rabbitExchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(topicExchange()).with(rabbitQueue);
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(Consumer consumer){
        return new MessageListenerAdapter(consumer, "receive");
    }

    @Bean
    public SimpleMessageListenerContainer container (ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(rabbitQueue);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }
}
