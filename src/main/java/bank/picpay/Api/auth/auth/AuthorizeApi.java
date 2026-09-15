package bank.picpay.Api.auth.auth;

import bank.picpay.Api.Exceptions.custom_exceptions.BusinessException;
import bank.picpay.Dominio.models.responses.auth.AuthorizationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthorizeApi {

    private final RestClient restClient;

    public AuthorizeApi(@Qualifier("authClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public boolean getAuth(){
        ResponseEntity<AuthorizationResponseDTO> response = restClient
                .get()
                .retrieve()
                .toEntity(AuthorizationResponseDTO.class);

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new BusinessException("Sistema de autorização retornando erro");
        }

        AuthorizationResponseDTO responseBody = response.getBody();
        if(responseBody.getData() != null){
            return responseBody.getData().isAuthorization();
        }else{
            throw new BusinessException("Sistema de autorização retornando response null");
        }
    }
}
