/* (C)2024 */
package com.jwang.urlshortener.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*~~(Migrate manually based on https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)~~>*/

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Autowired private UserDetailsService jwtUserDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public org.springframework.security.web.SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        return httpSecurity
                .csrf(csrf -> csrf
                        .disable())
                // dont authenticate this particular request
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/authenticate", "/register")
                        .permitAll()
                        .
                                // all other requests need to be authenticated
                                anyRequest()
                        .authenticated())
                .
                        // make sure we use stateless session; session won't be used to
                        // store user's state.
                        sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();

        // Add a filter to validate the tokens with every request
    }
}
