package com.unipoint.security.controller;

import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.unipoint.security.TestConstants.*;

@RestController
public class SecurityMockController {


    @GetMapping("/secured")
    public String securityTest() {
        return WORKING;
    }

    @GetMapping("/secured_1")
    public String securityRole_Test1(Principal principal) {
        if (principal == null) {
            return NOT_WORKING;
        }
        return WORKING;
    }

    @GetMapping("/secured_2")
    @PreAuthorize("hasRole('Test')")
    public String securityRole_Test2()
    {
        return WORKING;
    }
}
