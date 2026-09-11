package bank.picpay.controller;

import bank.picpay.api.controller.CarteiraController;
import bank.picpay.dominio.models.carteira.CarteiraDTO;
import bank.picpay.dominio.models.carteira.CarteiraEntity;
import bank.picpay.dominio.service.CarteiraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarteiraController.class)
class CarteiraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarteiraService carteiraService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void cadastrarCarteira() throws Exception {
        CarteiraDTO carteiraDTO = new CarteiraDTO(UUID.randomUUID());
        CarteiraEntity carteiraEntity = new CarteiraEntity();

        when(carteiraService.criarCarteira(any()))
                .thenReturn(new ResponseEntity<>(carteiraEntity, HttpStatus.CREATED));

        mockMvc.perform(post("/carteira")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carteiraDTO))
        ).andExpect(status().isCreated());
    }

    @Test
    void pesquisarCarteira() throws Exception {
        UUID id = UUID.randomUUID();
        CarteiraEntity carteiraEntity = new CarteiraEntity();

        when(carteiraService.criarCarteira(any()))
                .thenReturn(new ResponseEntity<>(carteiraEntity, HttpStatus.CREATED));

        mockMvc.perform(get("/carteira/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}