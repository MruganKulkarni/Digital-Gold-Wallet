package com.digitalgoldwallet.digital_gold_wallet.config;

import com.digitalgoldwallet.digital_gold_wallet.security.CustomAccessDeniedHandler;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /*
     * ============================================================
     * TANMAY
     * ============================================================
     */
    @Value("${tanmay.username}")
    private String tanmayUsername;

    @Value("${tanmay.password}")
    private String tanmayPassword;

    /*
     * ============================================================
     * VARSHA
     * ============================================================
     */
    @Value("${varsha.username}")
    private String varshaUsername;

    @Value("${varsha.password}")
    private String varshaPassword;

    /*
     * ============================================================
     * SPARSH
     * ============================================================
     */
    @Value("${sparsh.username}")
    private String sparshUsername;

    @Value("${sparsh.password}")
    private String sparshPassword;

    /*
     * ============================================================
     * MRUGAN
     * ============================================================
     */
    @Value("${mrugan.username}")
    private String mruganUsername;

    @Value("${mrugan.password}")
    private String mruganPassword;

    /*
     * ============================================================
     * PASSWORD ENCODER
     * ============================================================
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /*
     * ============================================================
     * USERS
     * ============================================================
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService(
            PasswordEncoder passwordEncoder
    ) {

        UserDetails tanmay =
                User.builder()
                        .username(tanmayUsername)
                        .password(
                                passwordEncoder.encode(
                                        tanmayPassword
                                )
                        )
                        .roles("PAYMENT_DEV")
                        .build();

        UserDetails varsha =
                User.builder()
                        .username(varshaUsername)
                        .password(
                                passwordEncoder.encode(
                                        varshaPassword
                                )
                        )
                        .roles("USER_DEV")
                        .build();

        UserDetails sparsh =
                User.builder()
                        .username(sparshUsername)
                        .password(
                                passwordEncoder.encode(
                                        sparshPassword
                                )
                        )
                        .roles("VENDOR_DEV")
                        .build();

        UserDetails mrugan =
                User.builder()
                        .username(mruganUsername)
                        .password(
                                passwordEncoder.encode(
                                        mruganPassword
                                )
                        )
                        .roles("GOLD_DEV")
                        .build();

        return new InMemoryUserDetailsManager(
                tanmay,
                varsha,
                sparsh,
                mrugan
        );
    }

    /*
     * ============================================================
     * SECURITY FILTER CHAIN
     * ============================================================
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                /*
                 * ====================================================
                 * DISABLE CSRF
                 * ====================================================
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * ====================================================
                 * AUTHORIZATION RULES
                 * ====================================================
                 */
                .authorizeHttpRequests(auth -> auth

                        /*
                         * SWAGGER
                         */
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        /*
                         * TANMAY
                         */
                        .requestMatchers(
                                "/api/v1/payments/**",
                                "/api/v1/wallets/**",
                                "/api/v1/users/*/payments",
                                "/api/v1/users/*/balance"
                        ).hasRole("PAYMENT_DEV")

                        /*
                         * VARSHA
                         */
                        .requestMatchers(
                                "/api/v1/users/**",
                                "/api/v1/addresses/**"
                        ).hasRole("USER_DEV")

                        /*
                         * SPARSH
                         */
                        .requestMatchers(
                                "/api/v1/vendors/**",
                                "/api/v1/branches/**"
                        ).hasRole("VENDOR_DEV")

                        /*
                         * MRUGAN
                         */
                        .requestMatchers(
                                "/api/v1/gold/**",
                                "/api/v1/physical-transactions/**",
                                "/api/v1/reports/**"
                        ).hasRole("GOLD_DEV")

                        .anyRequest()
                        .authenticated()
                )

                /*
                 * ====================================================
                 * HTTP BASIC AUTH
                 * ====================================================
                 */
                .httpBasic(Customizer.withDefaults())

                /*
                 * ====================================================
                 * ACCESS DENIED
                 * ====================================================
                 */
                .exceptionHandling(ex -> ex

                        .accessDeniedHandler(
                                new CustomAccessDeniedHandler()
                        )
                );

        return http.build();
    }
}