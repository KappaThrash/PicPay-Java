package bank.picpay.Dominio.Carteira.Servicos;

import bank.picpay.Api.exceptions.custom_exceptions.CarteiraNotFoundException;
import bank.picpay.Api.exceptions.custom_exceptions.UserNotFoundException;
import bank.picpay.Dominio.Carteira.Dtos.CarteiraDTO;
import bank.picpay.Dominio.Carteira.Entidades.CarteiraEntity;
import bank.picpay.Infra.repository.CarteiraRepository;
import bank.picpay.Infra.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CarteiraService {
    private final CarteiraRepository carteiraRepository;
    private final UsuarioRepository usuarioRepository;

    CarteiraService(CarteiraRepository carteiraRepository, UsuarioRepository usuarioRepository){
        this.carteiraRepository = carteiraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<CarteiraEntity> criarCarteira(CarteiraDTO dto){
        var UsuarioEntity = usuarioRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        var Carteira = new CarteiraEntity();
        Carteira.mapDTOToEntity(UsuarioEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(carteiraRepository.save(Carteira));
    }

    public ResponseEntity<CarteiraEntity> getCarteira(UUID id) {
        var carteiraEntity = carteiraRepository.findById(id)
                .orElseThrow(() -> new CarteiraNotFoundException("Carteira não encontrada"));

        return ResponseEntity.status(HttpStatus.CREATED).body(carteiraEntity);
    }
}
