package bank.picpay.dominio.models.carteira;

import bank.picpay.api.exceptions.custom_exceptions.BusinessException;
import bank.picpay.dominio.Usuario.Entidades.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carteira")
public class CarteiraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UsuarioEntity user_id;

    @Column(nullable = false)
    private BigDecimal balance;

    public BigDecimal debit(BigDecimal value){
        if(this.getBalance().compareTo(value) < 0){
            throw new BusinessException("Saldo insuficiente");
        }
        this.balance = this.balance.subtract(value);

        return this.balance;
    }

    public BigDecimal credit(BigDecimal value){
        if(value.compareTo(BigDecimal.ZERO) < 0){
            throw new BusinessException("Creditando valor negativo");
        }
        this.balance = this.balance.add(value);

        return this.balance;
    }

    public void mapDTOToEntity(UsuarioEntity usuarioEntity){
        this.user_id = usuarioEntity;
        this.balance = BigDecimal.ZERO;
    }
}
