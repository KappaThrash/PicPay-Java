package bank.picpay.controller;

import bank.picpay.Api.Controller.TransacaoController;
import bank.picpay.Dominio.Transacao.Dto.TransacaoDTO;
import bank.picpay.Dominio.Transacao.Entidades.TransacaoEntity;
import bank.picpay.Dominio.Transacao.Servicos.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransacaoController.class)
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransacaoService transacaoService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void transacaoPost() throws Exception{
        TransacaoDTO transacaoDTO = new TransacaoDTO(new BigDecimal(100), UUID.randomUUID(),UUID.randomUUID());

        TransacaoEntity transacaoEntity = new TransacaoEntity();

        when(transacaoService.actTransacao(any()))
                .thenReturn(new ResponseEntity<>(transacaoEntity,HttpStatus.CREATED));

        mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacaoDTO)))
                .andExpect(status().isCreated());
    }
}