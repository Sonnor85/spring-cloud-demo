package hu.siz.spring.cloud.authentication.controller;

import hu.siz.spring.cloud.authentication.api.AuthenticationAPI;
import hu.siz.spring.cloud.authentication.model.AuthenticationRequest;
import hu.siz.spring.cloud.authentication.model.AuthenticationResponse;
import hu.siz.spring.cloud.authentication.service.JwtTokenService;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationController implements AuthenticationAPI {

    private final JwtTokenService jwtTokenService;

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        log.info("Authentication attempt for user {}", username);
        //TODO do some real authentication here
        return buildTokenResponse(username);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {
        log.info("Token refresh");
        String authorization = request.getHeader("Authorization");
        String token = authorization == null ? null : authorization.replace("Bearer ", "");
        try {
            final String username = jwtTokenService.getUsernameFromToken(token);
            log.info("Generating new token for user {}", username);
            return buildTokenResponse(username);
        } catch (JwtException e) {
            log.warn("JWT token decoding error: {}", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private ResponseEntity<AuthenticationResponse> buildTokenResponse(String username) {
        return ResponseEntity.ok(new AuthenticationResponse(jwtTokenService.generateToken(username),
                jwtTokenService.generateRefreshToken(username)));
    }
}
