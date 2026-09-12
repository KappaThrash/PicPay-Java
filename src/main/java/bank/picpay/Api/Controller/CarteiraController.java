package bank.picpay.Api.Controller;

import bank.picpay.Dominio.Carteira.Dtos.CarteiraDTO;
import bank.picpay.Dominio.Carteira.Entidades.CarteiraEntity;
import bank.picpay.Dominio.Carteira.Servicos.CarteiraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carteira")
public class CarteiraController {
    private final CarteiraService service;

    @PostMapping
    public ResponseEntity<CarteiraEntity> cadastrarCarteira(@RequestBody @Valid CarteiraDTO dto){
        return service.criarCarteira(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarteiraEntity> pesquisarCarteira(@PathVariable UUID id){
        return service.getCarteira(id);
    }
}
