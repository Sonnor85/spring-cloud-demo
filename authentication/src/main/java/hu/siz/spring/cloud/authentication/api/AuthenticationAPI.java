package hu.siz.spring.cloud.authentication.api;

import hu.siz.spring.cloud.authentication.model.AuthenticationRequest;
import hu.siz.spring.cloud.authentication.model.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/api/v1")
@Tag(name = "Authentication")
public interface AuthenticationAPI {
    @PostMapping("/authenticate")
    @Operation(summary = "Obtain tokens for authenticated operations")
    ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest);

    @GetMapping("/refreshtoken")
    @Operation(summary = "Refresh token using a renew token", security = @SecurityRequirement(name = "jwt"))
    ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request);
}
