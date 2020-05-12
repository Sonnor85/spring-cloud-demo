package hu.siz.spring.cloud.authentication.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authentication request")
public class AuthenticationRequest {
    @NotEmpty
    @Schema(description = "The username to authenticate", example = "user")
    private String username;
    @NotEmpty
    @Schema(description = "The password for the username", example = "AsffDsE_344")
    private String password;
}
