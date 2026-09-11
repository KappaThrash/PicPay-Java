package bank.picpay.dominio.models.transacao;


import bank.picpay.dominio.models.carteira.CarteiraEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transacoes")
public class TransacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private CarteiraEntity payer;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private CarteiraEntity payee;

    private Instant created_at;


    public void mapDTOToEntity(TransacaoDTO dto, CarteiraEntity payer,CarteiraEntity payee){
        this.amount = dto.getAmount();
        this.payer = payer;
        this.payee = payee;
        this.created_at = Instant.now();
    }
}
