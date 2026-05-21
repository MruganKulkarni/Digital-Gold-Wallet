package com.digitalgoldwallet.digital_gold_wallet.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

/*
 * ============================================================
 * CUSTOM ACCESS DENIED HANDLER
 * ============================================================
 *
 * For API requests (Accept: application/json) → returns JSON 403
 * For browser/Thymeleaf requests → redirects to /403 HTML page
 *
 * ============================================================
 */

public class CustomAccessDeniedHandler
        implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        String accept = request.getHeader("Accept");

        boolean isApiRequest = 
                (request.getRequestURI() != null && request.getRequestURI().startsWith("/api/")) ||
                (accept != null
                && accept.contains("application/json")
                && !accept.contains("text/html"));

        if (isApiRequest) {

            /*
             * REST API client — return JSON error response
             */
            response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN
            );

            response.setContentType(
                    "application/json"
            );

            Map<String, Object> error =
                    new HashMap<>();

            error.put(
                    "timestamp",
                    LocalDateTime.now().toString()
            );

            error.put(
                    "status",
                    403
            );

            error.put(
                    "error",
                    "Access Denied"
            );

            error.put(
                    "message",
                    "You are not authorized to access this endpoint"
            );

            new ObjectMapper()
                    .writeValue(
                            response.getOutputStream(),
                            error
                    );

        } else {

            /*
             * Browser / Thymeleaf request — redirect to 403 page
             */
            response.sendRedirect(
                    "/403"
            );

        }
    }
}