package com.digitalgoldwallet.digital_gold_wallet.controller;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

/*
 * ============================================================
 * LOGIN CONTROLLER
 * ============================================================
 */

@Controller
public class LoginController {

    /*
     * ============================================================
     * LOGIN PAGE
     * ============================================================
     */
    @GetMapping("/login")
    public String loginPage() {

        /*
         * Resolves to:
         * src/main/resources/templates/login.html
         */
        return "login";
    }

    /*
     * ============================================================
     * DASHBOARD PAGE
     * ============================================================
     */
    @GetMapping("/dashboard")
    public String dashboard(
            Authentication authentication,
            Model model
    ) {

        /*
         * Logged-in username
         */
        model.addAttribute(
                "username",
                authentication.getName()
        );

        /*
         * Logged-in user roles
         */
        model.addAttribute(
                "role",
                authentication.getAuthorities()
        );

        /*
         * Resolves to:
         * src/main/resources/templates/dashboard.html
         */
        return "dashboard";
    }
}