package bank.picpay.dominio.Usuario.Servicos;

import bank.picpay.dominio.Usuario.Entidades.UsuarioEntity;
import bank.picpay.dominio.Usuario.Entidades.cnpjDTO;
import bank.picpay.dominio.Usuario.Entidades.cpfDTO;
import bank.picpay.dominio.Usuario.Interfaces.IUsuarioService;
import bank.picpay.infra.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService implements IUsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<UsuarioEntity> validarUsuario(cpfDTO dto){
        var Entity = new UsuarioEntity();
        Entity.mapCPFDTOoEntity(dto);

        repository.save(Entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(Entity);
    }

    public ResponseEntity<UsuarioEntity> validarLojista(cnpjDTO dto){
        var Entity = new UsuarioEntity();
        Entity.mapCPNPJDTOoEntity(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(Entity));
    }
}
