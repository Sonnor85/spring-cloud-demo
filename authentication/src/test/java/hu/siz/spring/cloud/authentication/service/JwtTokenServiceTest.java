package hu.siz.spring.cloud.authentication.service;

import org.junit.Assert;
import org.junit.Test;

public class JwtTokenServiceTest {

    private static final String USERNAME = "proba";

    @Test
    public void generateToken() {
        final JwtTokenService service = new JwtTokenService();
        String token = service.generateToken(USERNAME);
        Assert.assertNotNull(token);
    }

    @Test
    public void generateRefreshToken() {
        final JwtTokenService service = new JwtTokenService();
        String token = service.generateRefreshToken(USERNAME);
        Assert.assertNotNull(token);
    }

    @Test
    public void getUsernameFromToken() {
        final JwtTokenService service = new JwtTokenService();
        String token = service.generateToken(USERNAME);
        Assert.assertNotNull(token);
        Assert.assertEquals(USERNAME, service.getUsernameFromToken(token));
    }
}
