package org.dgc.sandbox.jwt.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrmController
{
    @GetMapping("/content")
    public void requestLicense(String content)
    {
        System.out.println("Accessed");
    }
}
