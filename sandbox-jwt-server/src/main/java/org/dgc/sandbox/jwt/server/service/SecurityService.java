package org.dgc.sandbox.jwt.server.service;

import org.dgc.sandbox.jwt.server.domain.User;
import org.dgc.sandbox.jwt.server.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SecurityService
{
    @Autowired
    private SecurityRepository repository;

    public void registerUser(User user)
    {
        try
        {
            user.setId(user.getName());
            user.setPassword(new String(MessageDigest.getInstance("MD5").digest(user.getPassword().getBytes())));

            repository.save(user);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    public User authenticateUser(String name, String password)
    {
        User user = repository.findById(name).orElseThrow();

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

        return user;
    }
}
