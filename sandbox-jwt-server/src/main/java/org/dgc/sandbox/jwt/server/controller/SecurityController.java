package org.dgc.sandbox.jwt.server.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.dgc.sandbox.jwt.server.domain.User;
import org.dgc.sandbox.jwt.server.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SecurityController
{
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private SecurityService securityService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody User user)
    {
        try
        {
            securityService.registerUser(user);

            return ResponseEntity.ok().build();
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody User user)
    {
        try
        {
            String token = securityService.authenticateUser(user.getName(), user.getPassword());

            return ResponseEntity.status(HttpStatus.OK).header(HEADER_STRING, TOKEN_PREFIX + " " + token).build();
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
