package bank.picpay.Api.controller;

import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;
import bank.picpay.Dominio.Usuario.Dtos.CNPJDto;
import bank.picpay.Dominio.Usuario.Dtos.CPFDto;
import bank.picpay.Dominio.Usuario.Servicos.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/usuario")
    public ResponseEntity<UsuarioEntity> postUsuario(@RequestBody @Valid CPFDto dto){
        return service.validarUsuario(dto);
    }

    @PostMapping("/lojista")
    public ResponseEntity<UsuarioEntity> postUsuario(@RequestBody @Valid CNPJDto dto){
        return service.validarLojista(dto);
    }

}
