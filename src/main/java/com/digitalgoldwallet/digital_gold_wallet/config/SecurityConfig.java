package com.digitalgoldwallet.digital_gold_wallet.config;

import com.digitalgoldwallet.digital_gold_wallet.security.CustomAccessDeniedHandler;
import com.digitalgoldwallet.digital_gold_wallet.security.CustomAuthenticationEntryPoint;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

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
                         * ====================================================
                         * SWAGGER
                         * ====================================================
                         */
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).authenticated()




                        /*
                         * ====================================================
                         * LOGIN PAGE
                         * ====================================================
                         */
                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/api/auth/me"
                        ).permitAll()




                        /*
                         * ====================================================
                         * TANMAY - PAYMENT + WALLET
                         * ====================================================
                         */
                        .requestMatchers(

                                /*
                                 * Wallet APIs
                                 */
                                "/api/v1/wallets/*/debit",
                                "/api/v1/wallets/*/credit",
                                "/api/v1/wallets/*/balance",

                                /*
                                 * Payment APIs
                                 */
                                "/api/v1/payments",
                                "/api/v1/payments/*",
                                "/api/v1/users/*/payments"

                        ).hasRole("PAYMENT_DEV")




                        /*
                         * ====================================================
                         * VARSHA - USER + ADDRESS
                         * ====================================================
                         */
                        .requestMatchers(

                                /*
                                 * User APIs
                                 */
                                "/api/v1/users",
                                "/api/v1/users/*",

                                /*
                                 * Address APIs
                                 */
                                "/api/v1/addresses",
                                "/api/v1/addresses/*"

                        ).hasRole("USER_DEV")




                        /*
                         * ====================================================
                         * SPARSH - VENDOR + BRANCH
                         * ====================================================
                         */
                        .requestMatchers(

                                /*
                                 * Vendor APIs
                                 */
                                "/api/v1/vendors",
                                "/api/v1/vendors/*",
                                "/api/v1/vendors/*/price",
                                "/api/v1/vendors/*/branches",

                                /*
                                 * Vendor Branch APIs
                                 */
                                "/api/v1/branches/*",
                                "/api/v1/branches/*/inventory"

                        ).hasRole("VENDOR_DEV")




                        /*
                         * ====================================================
                         * MRUGAN - GOLD + TRANSACTIONS
                         * ====================================================
                         */
                        .requestMatchers(

                                /*
                                 * Physical Gold APIs
                                 */
                                "/api/v1/gold/physical/convert",
                                "/api/v1/users/*/gold/physical",
                                "/api/v1/physical-transactions/*",

                                /*
                                 * Virtual Gold APIs
                                 */
                                "/api/v1/gold/virtual/buy",
                                "/api/v1/gold/virtual/sell",
                                "/api/v1/users/*/gold/virtual",
                                "/api/v1/branches/*/gold/virtual",

                                /*
                                 * Transaction APIs
                                 */
                                "/api/v1/users/*/transactions",
                                "/api/v1/branches/*/transactions"

                        ).hasRole("GOLD_DEV")




                        /*
                         * ====================================================
                         * REPORTING APIs
                         * ====================================================
                         */
                        .requestMatchers(
                                "/api/v1/reports/**"
                        ).permitAll()




                        /*
                         * ====================================================
                         * ANY OTHER REQUEST
                         * ====================================================
                         */
                        .anyRequest()
                        .authenticated()
                )

                /*
                 * ====================================================
                 * FORM LOGIN
                 * ====================================================
                 */
                .formLogin(form -> form

                        /*
                         * Custom login page
                         */
                        .loginPage("/login")

                        /*
                         * Spring Security handles POST /login
                         */
                        .loginProcessingUrl("/login")

                        /*
                         * Redirect after successful login
                         */
                        .defaultSuccessUrl("/dashboard")

                        /*
                         * Login failure URL
                         */
                        .failureUrl("/login?error=true")

                        /*
                         * Permit login page
                         */
                        .permitAll()
                )

                /*
                 * ====================================================
                 * LOGOUT
                 * ====================================================
                 */
                .logout(logout -> logout

                        /*
                         * Logout endpoint
                         */
                        .logoutUrl("/logout")

                        /*
                         * Redirect after logout
                         */
                        .logoutSuccessUrl("/login?logout=true")

                        /*
                         * Invalidate session
                         */
                        .invalidateHttpSession(true)

                        /*
                         * Delete session cookie
                         */
                        .deleteCookies("JSESSIONID")

                        /*
                         * Permit logout
                         */
                        .permitAll()
                )

                .exceptionHandling(ex -> ex

                        .authenticationEntryPoint(
                                new CustomAuthenticationEntryPoint()
                        )

                        .accessDeniedHandler(
                                new CustomAccessDeniedHandler()
                        )
                );

        return http.build();
    }
}