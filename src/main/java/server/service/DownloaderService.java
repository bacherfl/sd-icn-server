package server.service;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import server.model.PrefetchInstruction;


/**
 * Created by florian on 06.05.15.
 */
@Service
public class DownloaderService {
    final static String queueName = "spring-boot";

    @Autowired
    RabbitTemplate rabbitTemplate;


    public void enqueueDownload(PrefetchInstruction instruction) {
        rabbitTemplate.convertAndSend(queueName, instruction);
    }
}
