package com.digitalgoldwallet.digital_gold_wallet.controller;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/*
 * ============================================================
 * AUTH CONTROLLER
 * ============================================================
 */

@RestController
public class AuthController {

    /*
     * ============================================================
     * GET CURRENT LOGGED-IN ROLE
     * ============================================================
     */
    @GetMapping("/api/auth/me")
    public Map<String, String> getCurrentUser(
            Authentication authentication
    ) {

        /*
         * Response map
         */
        Map<String, String> response =
                new HashMap<>();

        /*
         * Username
         */
        response.put(
                "username",
                authentication.getName()
        );

        /*
         * First role
         */
        GrantedAuthority authority =
                authentication
                        .getAuthorities()
                        .iterator()
                        .next();

        response.put(
                "role",
                authority.getAuthority()
        );

        return response;
    }
}