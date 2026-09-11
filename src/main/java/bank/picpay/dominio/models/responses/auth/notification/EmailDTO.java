package bank.picpay.dominio.models.responses.auth.notification;

import java.math.BigDecimal;
import java.time.Instant;

public record EmailDTO(String payerNome, String payeeNome, String payerEmail, String payeeEmail, BigDecimal transactionValue, Instant transactionTime) {
}
