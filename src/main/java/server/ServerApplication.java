package server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {
    /*
    final static String queueName = "spring-boot";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    Downloader receiver() {
        return new Downloader();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Downloader receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    */

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
