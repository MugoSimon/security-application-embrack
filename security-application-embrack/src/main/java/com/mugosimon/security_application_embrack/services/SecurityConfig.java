package com.mugosimon.security_application_embrack.services;

import com.mugosimon.security_application_embrack.jwt.AuthEntryPointJwt;
import com.mugosimon.security_application_embrack.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;


/**
 * The SecurityConfig class configures security settings for the application using Spring Security.
 * It defines beans and configurations for authentication, authorization, and security filter chains.
 * This class enables method-level security and configures various security-related components such as
 * user details service, password encoder, and JWT token filter.
 * <p>
 * Key Components:
 * - DataSource: Autowired to interact with the database for user details.
 * - AuthEntryPointJwt: Custom entry point to handle unauthorized access.
 * - AuthTokenFilter: Custom filter to process JWT tokens.
 * - SecurityFilterChain: Configures the security filter chain, including URL authorization rules,
 * session management, exception handling, HTTP basic authentication, CSRF protection, and JWT filter.
 * - UserDetailsService: Configures a JdbcUserDetailsManager to manage user details using the DataSource.
 * - CommandLineRunner: Initializes sample users (USER and ADMIN) in the database.
 * - PasswordEncoder: Configures a BCryptPasswordEncoder for encoding passwords.
 * - AuthenticationManager: Provides an authentication manager bean.
 */


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    AuthEntryPointJwt unauthorizedHandler;

    @Bean
    AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) ->
                requests
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/signIn").permitAll()
                        .anyRequest().authenticated());
        http.sessionManagement(
                session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
        //http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {

        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public CommandLineRunner initData(UserDetailsService userDetailsService) {
        return args -> {
            JdbcUserDetailsManager manager = (JdbcUserDetailsManager) userDetailsService;

            UserDetails userOne = User.withUsername("user")
                    .password(passwordEncoder().encode("userPassword"))
                    .roles("USER")
                    .build();

            UserDetails userTwo = User.withUsername("admin")
                    .password(passwordEncoder().encode("adminPassword"))
                    .roles("ADMIN")
                    .build();

            JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
            if (userDetailsManager.userExists(userOne.getUsername())) {
                userDetailsManager.deleteUser(userOne.getUsername());
            }
            if (userDetailsManager.userExists(userTwo.getUsername())) {
                userDetailsManager.deleteUser(userTwo.getUsername());
            }
            userDetailsManager.createUser(userOne);
            userDetailsManager.createUser(userTwo);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
