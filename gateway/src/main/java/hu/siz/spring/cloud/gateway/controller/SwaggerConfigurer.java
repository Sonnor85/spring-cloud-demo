package hu.siz.spring.cloud.gateway.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

/**
 * Creates a minimalistic router to return Swagger UI resources from webjars
 * <br>Could have been configurable but serves the purpose. Sorry.
 *
 * @author iszell
 * @since 0.0.1
 */
@Configuration
public class SwaggerConfigurer implements WebFluxConfigurer {

    private static final URI CONFIG_URI = URI.create("/swagger-ui/index.html?configUrl=" + SwaggerConfigController.CONFIG_PATH);

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(RequestPredicates.GET("/swagger-ui.html"), req ->
                ServerResponse.temporaryRedirect(CONFIG_URI).build());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/3.25.4/");
    }
}
