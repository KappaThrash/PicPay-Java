package bank.picpay.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean("authClient")
    public RestClient authClient(RestClient.Builder builder){
        return builder
                .baseUrl("https://util.devi.tools/api/v2/authorize")
                .build();
    }
}
