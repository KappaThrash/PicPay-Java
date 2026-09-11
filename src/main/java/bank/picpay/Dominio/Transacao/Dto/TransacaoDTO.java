package bank.picpay.Dominio.Transacao.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransacaoDTO {
    private BigDecimal amount;
    private UUID payer;
    private UUID payee;
}
