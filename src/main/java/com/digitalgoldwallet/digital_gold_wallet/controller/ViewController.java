package com.digitalgoldwallet.digital_gold_wallet.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

/*
 * ============================================================
 * THYMELEAF VIEW CONTROLLER
 * ============================================================
 *
 * Handles shared pages:
 * - Home page
 * - Login page
 * - 403 Access Denied page
 * - User module page
 * - Reports module page
 *
 * ============================================================
 */

@Controller
public class ViewController {

    /*
     * ============================================================
     * HOME PAGE / DASHBOARD
     * ============================================================
     */
    @GetMapping({"/", "/dashboard"})
    public String homePage() {

        return "home";
    }

    /*
     * ============================================================
     * LOGIN PAGE
     * ============================================================
     */
    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    /*
     * ============================================================
     * 403 ACCESS DENIED PAGE
     * ============================================================
     */
    @GetMapping("/403")
    public String accessDeniedPage() {

        return "403";
    }

    /*
     * ============================================================
     * USER MODULE PAGE
     * ============================================================
     */
    @GetMapping("/user-module")
    public String userModulePage() {

        return "user-module";
    }

    /*
     * ============================================================
     * REPORTS MODULE PAGE
     * ============================================================
     */
    @GetMapping("/reports-module")
    public String reportsModulePage() {

        return "reports-module";
    }

}