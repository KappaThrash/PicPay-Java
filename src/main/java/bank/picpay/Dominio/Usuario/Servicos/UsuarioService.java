package bank.picpay.Dominio.Usuario.Servicos;

import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;
import bank.picpay.Dominio.Usuario.Dtos.CNPJDto;
import bank.picpay.Dominio.Usuario.Dtos.CPFDto;
import bank.picpay.Dominio.Usuario.Interfaces.IUsuarioService;
import bank.picpay.Infra.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService implements IUsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<UsuarioEntity> validarUsuario(CPFDto dto){
        var Entity = new UsuarioEntity();
        Entity.mapCPFDTOoEntity(dto);

        repository.save(Entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(Entity);
    }

    public ResponseEntity<UsuarioEntity> validarLojista(CNPJDto dto){
        var Entity = new UsuarioEntity();
        Entity.mapCPNPJDTOoEntity(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(Entity));
    }
}
