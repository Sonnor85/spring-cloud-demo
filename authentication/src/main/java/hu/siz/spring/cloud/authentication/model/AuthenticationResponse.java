package hu.siz.spring.cloud.authentication.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response for successful authentication requests")
public class AuthenticationResponse {
    @Schema(description = "A JWT token used to access secured endpoints")
    private String token;
    @Schema(description = "A JWT token used to renew access tokens")
    private String refreshToken;
}
