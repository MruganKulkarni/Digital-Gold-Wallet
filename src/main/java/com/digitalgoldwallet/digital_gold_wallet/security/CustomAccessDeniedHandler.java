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
 * Handles:
 * 403 Forbidden
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
                LocalDateTime.now()
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
    }
}