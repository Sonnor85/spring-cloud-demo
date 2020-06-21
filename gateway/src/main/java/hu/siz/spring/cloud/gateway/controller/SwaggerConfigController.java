package hu.siz.spring.cloud.gateway.controller;

import hu.siz.spring.cloud.gateway.model.SwaggerConfig;
import hu.siz.spring.cloud.gateway.model.SwaggerUrl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class SwaggerConfigController {

    private static final String APP_METADATA_KEY = "app";
    public static final String CONFIG_PATH = "/v3/api-docs/swagger-config";

    private final RouteLocator routeLocator;

    private final BuildProperties buildProperties;

    /**
     * Dynamic config getter method to provide actual API configuration based on registered routes excluding the gateway itself
     *
     * @return an OpenAPI configuration
     */
    @GetMapping(CONFIG_PATH)
    public SwaggerConfig getConfig() {
        SwaggerConfig config = new SwaggerConfig();
        List<SwaggerUrl> urls = new ArrayList<>();
        routeLocator.getRoutes().filter(route -> !buildProperties.getArtifact().equals(route.getMetadata().get(APP_METADATA_KEY)))
                .subscribe(route -> {
                    String app = (String) route.getMetadata().get("app");
                    urls.add(new SwaggerUrl(app, "/v3/" + app + "/api-docs"));
                });
        config.setUrls(urls);
        return config;
    }
}
