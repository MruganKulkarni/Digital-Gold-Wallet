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

        String accept = request.getHeader("Accept");

        boolean isApiRequest =
                (request.getRequestURI() != null && request.getRequestURI().startsWith("/api/")) ||
                (accept != null
                && accept.contains("application/json")
                && !accept.contains("text/html"));

        if (isApiRequest) {
            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );
            response.setContentType(
                    "application/json"
            );
            response.getWriter().write(
                    """
                    {
                        "status": 401,
                        "error": "Unauthorized",
                        "message": "You need to log in to access this endpoint."
                    }
                    """
            );
        } else {
            response.sendRedirect("/login");
        }
    }
}