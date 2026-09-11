package bank.picpay.dominio.models.usuario;

import jakarta.persistence.*;
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

    String nome;

    @Enumerated(EnumType.STRING)
    TipoUsuario tipo;

    @Column(unique = true)
    String documento;

    @Column(unique = true)
    String email;

    String senha;


    public boolean isLOJISTA(){
        return this.tipo == TipoUsuario.LOJISTA;
    }

    public void mapCPFDTOoEntity(cpfDTO dto){
        this.nome = dto.getNome();
        this.tipo = TipoUsuario.USUARIO;
        this.documento = dto.getCpf();
        this.email = dto.getEmail();
        this.senha = dto.getSenha();
    }

    public void mapCPNPJDTOoEntity(cnpjDTO dto){
        this.nome = dto.getNome();
        this.tipo = TipoUsuario.LOJISTA;
        this.documento = dto.getCnpj();
        this.email = dto.getEmail();
        this.senha = dto.getSenha();
    }
}
