package hu.siz.spring.cloud.gateway.model;

import lombok.Data;

import java.util.List;

/**
 * OpenAPI configuration class. In this implementation only API documentation URLs are returned
 *
 * @author iszell
 * @since 0.0.1
 */
@Data
public class SwaggerConfig {

    private List<SwaggerUrl> urls;

}
