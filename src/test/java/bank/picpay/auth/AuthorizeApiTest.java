package bank.picpay.auth;

import bank.picpay.Api.auth.auth.AuthorizeApi;
import bank.picpay.Api.exceptions.custom_exceptions.BusinessException;
import bank.picpay.Dominio.models.responses.auth.AuthorizationResponseDTO;
import bank.picpay.Dominio.models.responses.auth.DataResponseAuth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizeApiTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuthorizeApi authorizeApi = new AuthorizeApi();

    @Test
    void getAuthShouldReturnTrueAsResponseIsTrue() {
        var response = new AuthorizationResponseDTO("success",new DataResponseAuth(true));
        when(restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", AuthorizationResponseDTO.class))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        assertTrue(authorizeApi.getAuth());
    }

    @Test
    void getAuthShouldReturnFalseAsResponseIsFalse() {
        var response = new AuthorizationResponseDTO("failed",new DataResponseAuth(false));
        when(restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", AuthorizationResponseDTO.class))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        assertFalse(authorizeApi.getAuth());
    }

    @Test
    void getAuthCase1() {
        var response = new AuthorizationResponseDTO("success",new DataResponseAuth(true));
        when(restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", AuthorizationResponseDTO.class))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));

        assertThrows(BusinessException.class, () -> authorizeApi.getAuth());
    }

    @Test
    void getAuthCase2() {
        var response = new AuthorizationResponseDTO(null,null);
        when(restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", AuthorizationResponseDTO.class))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        assertThrows(BusinessException.class, () -> authorizeApi.getAuth());
    }
}