package hu.siz.spring.cloud.authentication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "hu.siz.spring.cloud.authentication")
@NoArgsConstructor
@Slf4j
@OpenAPIDefinition(info = @Info(title = "Authentication APIs", version = "0.0.1"))
@SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", name = "jwt")
public class AuthenticationApplication {
    /**
     * Main method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }
}
