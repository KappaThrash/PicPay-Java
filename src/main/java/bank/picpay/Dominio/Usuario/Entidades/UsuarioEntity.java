package bank.picpay.Dominio.Usuario.Entidades;

import bank.picpay.Dominio.Usuario.Dtos.CNPJDto;
import bank.picpay.Dominio.Usuario.Dtos.CPFDto;
import bank.picpay.Dominio.Usuario.Enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name = "usuario_id")
    @JsonProperty("usuario_id")
    private UUID usuarioId;

    @Size(max = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    @Column(unique = true)
    private String documento;

    @Column(unique = true)
    private String email;

    @Size(max = 840)
    private String senha;

    public boolean isLojista(){
        return this.tipo == TipoUsuario.LOJISTA;
    }

    public void mapCpfDtoToEntity(CPFDto dto){
        this.nome = dto.getNome();
        this.tipo = TipoUsuario.USUARIO;
        this.documento = dto.getCpf();
        this.email = dto.getEmail();
    }

    public void mapCnpjDtoToEntity(CNPJDto dto){
        this.nome = dto.getNome();
        this.tipo = TipoUsuario.LOJISTA;
        this.documento = dto.getCnpj();
        this.email = dto.getEmail();
    }
}
