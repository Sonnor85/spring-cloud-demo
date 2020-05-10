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
 * Main class for the Framework Spring Boot runner
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
    public static final String STRATEGY = DedupeResponseHeaderGatewayFilterFactory.Strategy.RETAIN_LAST.name();
    private static final String MAPPING = "/(?<remaining>.*)";
    private static final String REPLACEMENT = "/${remaining}";

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
                .route("base", (PredicateSpec r) -> r
                        .path("/base/**")
                        .filters(f -> f.rewritePath("/base" + MAPPING, REPLACEMENT).
                                dedupeResponseHeader(HEADER_NAME, STRATEGY))
                        .uri("http://localhost:8081"))
                .route("jobs", (PredicateSpec r) -> r
                        .path("/jobs/**")
                        .filters(f -> f.rewritePath("/jobs" + MAPPING, REPLACEMENT).
                                dedupeResponseHeader(HEADER_NAME, STRATEGY))
                        .uri("http://localhost:8082"))
                .route("ordermodule", (PredicateSpec r) -> r
                        .path("/ordermodule/**")
                        .filters(f -> f.rewritePath("/ordermodule" + MAPPING, REPLACEMENT).
                                dedupeResponseHeader(HEADER_NAME, STRATEGY))
                        .uri("http://localhost:8083"))
                .build();
    }
}

