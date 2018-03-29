package org.dgc.sandbox.jwt.server.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.dgc.sandbox.jwt.server.domain.User;
import org.dgc.sandbox.jwt.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class SecurityService
{
    private static final int EXP_LENGTH = 86400000;

    @Autowired
    private UserRepository userRepository;

    @Value("${application.properties.security.secret}")
    private String secret;

    public void registerUser(User user)
    {
        try
        {
            user.setId(user.getName());
            user.setPassword(new String(MessageDigest.getInstance("MD5").digest(user.getPassword().getBytes())));

            userRepository.save(user);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    public String authenticateUser(String name, String password)
    {
        User user = userRepository.findById(name).orElseThrow();

        try
        {
            if (!user.getPassword().equals(new String(MessageDigest.getInstance("MD5").digest(password.getBytes()))))
            {
                throw new RuntimeException("Incorrect password");
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return Jwts.builder()
                .setSubject(user.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXP_LENGTH))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
