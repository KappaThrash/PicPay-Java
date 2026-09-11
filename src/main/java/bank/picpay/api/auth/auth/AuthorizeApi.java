package bank.picpay.api.auth.auth;

import bank.picpay.api.exceptions.custom_exceptions.BusinessException;
import bank.picpay.dominio.models.responses.auth.AuthorizationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthorizeApi {

    @Autowired
    private RestTemplate restTemplate;

    public boolean getAuth(){
        ResponseEntity<AuthorizationResponseDTO> response = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", AuthorizationResponseDTO.class);

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
