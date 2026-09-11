package bank.picpay.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${broker.queue.email.name}")
    private String queue;

    @Bean
    public Queue queue(){
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", "email.dlx")
                .withArgument("x-dead-letter-routing-key", "email.dead.letter")
                .build();
    }

    @Bean
    public Queue auditQueue(){
        return QueueBuilder.durable(queue + ".dlq").build();
    }
    @Bean
    public DirectExchange directExchangeEmail(){
        return new DirectExchange("email.dlx");
    }

    @Bean
    public Binding bindingAudit(){
        return BindingBuilder
                .bind(auditQueue())
                .to(directExchangeEmail())
                .with("email.dead.letter");
    }

    @Bean
    public MessageConverter messageConverter(){
        return new JacksonJsonMessageConverter();
    }
}
