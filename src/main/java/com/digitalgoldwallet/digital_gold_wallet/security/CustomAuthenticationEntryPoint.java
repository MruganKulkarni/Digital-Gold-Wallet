package com.digitalgoldwallet.digital_gold_wallet.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/*
 * ============================================================
 * CUSTOM AUTHENTICATION ENTRY POINT
 * ============================================================
 *
 * Handles:
 * - invalid credentials
 * - unauthenticated access
 *
 * ============================================================
 */

public class CustomAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        /*
         * ====================================================
         * HTTP STATUS
         * ====================================================
         */
        response.setStatus(
                HttpServletResponse.SC_UNAUTHORIZED
        );

        /*
         * ====================================================
         * THIS TEXT APPEARS IN BROWSER LOGIN POPUP
         * ====================================================
         */
        response.setHeader(
                "WWW-Authenticate",
                "Basic realm=\"Invalid credentials. Please login again.\""
        );

        /*
         * ====================================================
         * RESPONSE TYPE
         * ====================================================
         */
        response.setContentType(
                "application/json"
        );

        /*
         * ====================================================
         * RESPONSE BODY
         * ====================================================
         */
        response.getWriter().write(
                """
                {
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "Invalid username or password. Please login again."
                }
                """
        );
    }
}