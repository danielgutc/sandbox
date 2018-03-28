package org.dgc.sandbox.jwt.server.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.dgc.sandbox.jwt.server.domain.User;
import org.dgc.sandbox.jwt.server.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SecurityController
{
    public static final int EXP_LENGTH = 86400000;
    @Autowired
    private SecurityService securityService;

    @Value("${application.properties.security.secret}")
    private String secret;

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
            User authUser = securityService.authenticateUser(user.getName(), user.getPassword());

            String jwt = Jwts.builder()
                    .setSubject(authUser.getName())
                    .setExpiration(new Date(System.currentTimeMillis() + EXP_LENGTH))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();

            return ResponseEntity.ok(jwt);
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
