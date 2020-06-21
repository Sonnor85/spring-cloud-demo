package hu.siz.spring.cloud.gateway;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.factory.DedupeResponseHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * Main class for the Gateway Spring Boot runner
 *
 * @author iszell
 * @since 0.0.1
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "hu.siz.spring.cloud.gateway")
@NoArgsConstructor
@Slf4j
public class GatewayApplication {

    private static final String HEADER_NAME = "Access-Control-Allow-Origin";
    private static final String STRATEGY = DedupeResponseHeaderGatewayFilterFactory.Strategy.RETAIN_LAST.name();
    private static final String API_TAG = "/(?<apitag>.*)";
    private static final String MAPPING = "/(?<remaining>.*)";
    private static final String REPLACEMENT = "/${apitag}/${remaining}";

    /**
     * Main method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * Create static routes for local development
     * with URL rewrite to match local ports and adding response header deduplication filter
     *
     * @param routeBuilder a builder for routes
     * @return the configured route locator
     */
    @Bean
    @Profile("!kubernetes")
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeBuilder) {
        log.info("Creating static routes");
        return routeBuilder.routes()
                .route("authentication", (PredicateSpec r) -> r
                        .path("/api/v1/authentication/**", "/v3/authentication/**")
                        .filters(f -> f.rewritePath(API_TAG + "/authentication" + MAPPING, REPLACEMENT).
                                dedupeResponseHeader(HEADER_NAME, STRATEGY))
                        .uri("http://localhost:8081")
                        .metadata("app", "authentication"))
                .build();
    }
}
