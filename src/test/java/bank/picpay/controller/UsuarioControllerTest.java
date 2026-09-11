package bank.picpay.controller;

import bank.picpay.Api.controller.UsuarioController;
import bank.picpay.Dominio.Usuario.Factories.UsuarioFactory;
import bank.picpay.Dominio.Usuario.Entidades.UsuarioEntity;
import bank.picpay.Dominio.Usuario.Dtos.CNPJDto;
import bank.picpay.Dominio.Usuario.Dtos.CPFDto;
import bank.picpay.Dominio.Usuario.Servicos.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    void postUsuarioCPF() throws Exception {
        ResponseEntity<UsuarioEntity> usuarioEntity = new ResponseEntity<>( UsuarioFactory.usuarioTipoUSUARIO(), HttpStatus.CREATED);
        CPFDto cpfDTO = new CPFDto(usuarioEntity.getBody().getNome(),usuarioEntity.getBody().getTipo(),
                usuarioEntity.getBody().getDocumento(),usuarioEntity.getBody().getEmail(),usuarioEntity.getBody().getSenha());

        when(usuarioService.validarUsuario(any(CPFDto.class)))
                .thenReturn(usuarioEntity);

        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cpfDTO))
        ).andExpect(status().isCreated());
    }

    @Test
    void postUsuarioCNPJ() throws Exception{
        ResponseEntity<UsuarioEntity> usuarioEntity = new ResponseEntity<>( UsuarioFactory.usuarioTipoLOJISTA(), HttpStatus.CREATED);
        CNPJDto cnpjDTO = new CNPJDto(usuarioEntity.getBody().getNome(),usuarioEntity.getBody().getTipo(),
                usuarioEntity.getBody().getDocumento(),usuarioEntity.getBody().getEmail(),usuarioEntity.getBody().getSenha());

        when(usuarioService.validarLojista(any(CNPJDto.class)))
                .thenReturn(usuarioEntity);

        mockMvc.perform(post("/lojista")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cnpjDTO))
        ).andExpect(status().isCreated());
    }
}