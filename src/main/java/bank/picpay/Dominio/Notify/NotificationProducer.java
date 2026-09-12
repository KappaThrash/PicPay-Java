package bank.picpay.Dominio.Notify;

import bank.picpay.Dominio.Notify.Dto.EmailDTO;
import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@Component
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void postTransactionNotification(UsuarioEntity payerAccount, UsuarioEntity payeeAccount, BigDecimal transactionValue, Instant transactionTime){
        EmailDTO emailDTO = new EmailDTO(payerAccount.getNome(), payeeAccount.getNome(), payerAccount.getEmail(),
                payeeAccount.getEmail(), transactionValue, transactionTime);

        rabbitTemplate.convertAndSend(routingKey, emailDTO);
        log.info("Notificação enviada a fila {}!", routingKey);
    }
}
