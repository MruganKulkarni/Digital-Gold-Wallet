package com.digitalgoldwallet.digital_gold_wallet.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

/*
 * ============================================================
 * THYMELEAF VIEW CONTROLLER
 * ============================================================
 */

@Controller
public class ViewController {

    /*
     * ============================================================
     * HOME PAGE
     * ============================================================
     */
    @GetMapping("/")
    public String homePage() {

        return "home";
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