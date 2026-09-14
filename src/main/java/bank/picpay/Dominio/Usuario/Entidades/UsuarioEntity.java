package bank.picpay.Dominio.Usuario.Entidades;

import bank.picpay.Dominio.Usuario.Dtos.CNPJDto;
import bank.picpay.Dominio.Usuario.Dtos.CPFDto;
import bank.picpay.Dominio.Usuario.Enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID usuario_id;

    @Size(max = 100)
    String nome;

    @Enumerated(EnumType.STRING)
    TipoUsuario tipo;

    @Column(unique = true)
    String documento;

    @Column(unique = true)
    String email;

    @Size(max = 840)
    String senha;

    public boolean isLOJISTA(){
        return this.tipo == TipoUsuario.LOJISTA;
    }

    public void mapCPFDTOoEntity(CPFDto dto){
        this.nome = dto.getNome();
        this.tipo = TipoUsuario.USUARIO;
        this.documento = dto.getCpf();
        this.email = dto.getEmail();
    }
    public void mapCPNPJDTOoEntity(CNPJDto dto){
        this.nome = dto.getNome();
        this.tipo = TipoUsuario.LOJISTA;
        this.documento = dto.getCnpj();
        this.email = dto.getEmail();
    }
}
