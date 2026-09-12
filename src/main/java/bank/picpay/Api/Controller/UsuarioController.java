package bank.picpay.Api.Controller;

import bank.picpay.Dominio.Usuario.Dtos.UsuarioDto;
import bank.picpay.Dominio.Usuario.Dtos.CNPJDto;
import bank.picpay.Dominio.Usuario.Dtos.CPFDto;
import bank.picpay.Dominio.Usuario.Servicos.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping("/usuario")
    public ResponseEntity<UsuarioDto> postUsuario(@RequestBody @Valid CPFDto dto){
        return service.validarUsuario(dto);
    }

    @PostMapping("/lojista")
    public ResponseEntity<UsuarioDto> postUsuario(@RequestBody @Valid CNPJDto dto){
        return service.validarLojista(dto);
    }

}
