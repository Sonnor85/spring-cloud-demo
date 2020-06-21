package hu.siz.spring.cloud.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An OpenAPI API documentation URL object
 */
@Getter
@AllArgsConstructor
public class SwaggerUrl {
    String name;
    String url;
}
