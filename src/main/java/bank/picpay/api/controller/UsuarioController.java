package bank.picpay.api.controller;

import bank.picpay.dominio.Usuario.Entidades.UsuarioEntity;
import bank.picpay.dominio.Usuario.Entidades.cnpjDTO;
import bank.picpay.dominio.Usuario.Entidades.cpfDTO;
import bank.picpay.dominio.Usuario.Servicos.UsuarioService;
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
    public ResponseEntity<UsuarioEntity> postUsuario(@RequestBody @Valid cpfDTO dto){
        return service.validarUsuario(dto);
    }

    @PostMapping("/lojista")
    public ResponseEntity<UsuarioEntity> postUsuario(@RequestBody @Valid cnpjDTO dto){
        return service.validarLojista(dto);
    }

}
