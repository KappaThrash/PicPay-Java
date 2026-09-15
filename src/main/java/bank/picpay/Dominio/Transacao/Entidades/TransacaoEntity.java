package bank.picpay.Dominio.Transacao.Entidades;

import bank.picpay.Dominio.Carteira.Entidades.CarteiraEntity;
import bank.picpay.Dominio.Transacao.Dto.TransacaoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private UUID id;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private CarteiraEntity payer;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private CarteiraEntity payee;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Instant createdAt;

    public void mapDtoToEntity(TransacaoDTO dto, CarteiraEntity payer, CarteiraEntity payee){
        this.amount = dto.getAmount();
        this.payer = payer;
        this.payee = payee;
        this.createdAt = Instant.now();
    }
}
