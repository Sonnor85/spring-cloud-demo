package hu.siz.spring.cloud.authentication;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * Main class for the Authentication Spring Boot application
 *
 * @author iszell
 * @since 0.0.1
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "hu.siz.spring.cloud.authentication")
@NoArgsConstructor
@Slf4j
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

    /**
     * Customize API documentation with artifact version from <code>pom.xml</code>
     *
     * @param buildProperties the build information from <code>pom.xml</code>
     *                        <br>Requires <code>build-info</code> goal for <code>spring-boot-maven-plugin</code>
     * @return a customized OpenAPI definition object
     */
    @Bean
    public OpenAPI openAPI(BuildProperties buildProperties) {
        OpenAPI api = new OpenAPI();
        api.setInfo(new Info().title(buildProperties.getName()).version(buildProperties.getVersion()));
        return api;
    }
}
