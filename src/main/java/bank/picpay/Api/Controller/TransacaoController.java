package bank.picpay.Api.Controller;

import bank.picpay.Dominio.Transacao.Dto.TransacaoDTO;
import bank.picpay.Dominio.Transacao.Entidades.TransacaoEntity;
import bank.picpay.Dominio.Transacao.Servicos.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService service;

    @PostMapping
    public ResponseEntity<TransacaoEntity> transacaoPost(@RequestBody @Valid TransacaoDTO dto){
        return service.actTransacao(dto);
    }
}
