package bank.picpay.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Value("${app.security.bcrypt.cost}")
    private int cost;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(cost);
    }
}
