package bank.picpay.controller;

import bank.picpay.api.controller.UsuarioController;
import bank.picpay.dominio.factories.factories.UsuarioFactory;
import bank.picpay.dominio.models.usuario.UsuarioEntity;
import bank.picpay.dominio.models.usuario.cnpjDTO;
import bank.picpay.dominio.models.usuario.cpfDTO;
import bank.picpay.dominio.service.UsuarioService;
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
        cpfDTO cpfDTO = new cpfDTO(usuarioEntity.getBody().getNome(),usuarioEntity.getBody().getTipo(),
                usuarioEntity.getBody().getDocumento(),usuarioEntity.getBody().getEmail(),usuarioEntity.getBody().getSenha());

        when(usuarioService.validarUsuario(any(cpfDTO.class)))
                .thenReturn(usuarioEntity);

        mockMvc.perform(post("/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cpfDTO))
        ).andExpect(status().isCreated());
    }

    @Test
    void postUsuarioCNPJ() throws Exception{
        ResponseEntity<UsuarioEntity> usuarioEntity = new ResponseEntity<>( UsuarioFactory.usuarioTipoLOJISTA(), HttpStatus.CREATED);
        cnpjDTO cnpjDTO = new cnpjDTO(usuarioEntity.getBody().getNome(),usuarioEntity.getBody().getTipo(),
                usuarioEntity.getBody().getDocumento(),usuarioEntity.getBody().getEmail(),usuarioEntity.getBody().getSenha());

        when(usuarioService.validarLojista(any(cnpjDTO.class)))
                .thenReturn(usuarioEntity);

        mockMvc.perform(post("/lojista")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cnpjDTO))
        ).andExpect(status().isCreated());
    }
}