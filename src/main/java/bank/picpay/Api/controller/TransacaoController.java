package bank.picpay.Api.controller;

import bank.picpay.Dominio.Transacao.Dto.TransacaoDTO;
import bank.picpay.Dominio.Transacao.Servicos.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransacaoController {
    private final TransacaoService service;

    public TransacaoController(TransacaoService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> transacaoPost(@RequestBody @Valid TransacaoDTO dto){
        return service.actTransacao(dto);
    }
}
