package bank.picpay.Dominio.Notify.Dto;

import java.math.BigDecimal;
import java.time.Instant;

public record EmailDTO(String payerNome, String payeeNome, String payerEmail, String payeeEmail, BigDecimal transactionValue, Instant transactionTime) {
}
