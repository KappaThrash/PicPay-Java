package bank.picpay.api.controller;

import bank.picpay.dominio.models.carteira.CarteiraDTO;
import bank.picpay.dominio.service.CarteiraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {
    private final CarteiraService service;

    CarteiraController(CarteiraService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarCarteira(@RequestBody @Valid CarteiraDTO dto){
        return service.criarCarteira(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> pesquisarCarteira(@PathVariable UUID id){
        return service.getCarteira(id);
    }
}
