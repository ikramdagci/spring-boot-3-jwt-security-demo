package com.ikramdagci.security.config;

import com.ikramdagci.security.component.JwtAuthenticationFilter;
import com.ikramdagci.security.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ikramdagci.security.user.Permission.*;
import static com.ikramdagci.security.user.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(),MANAGER.name())
                        .requestMatchers(GET,"/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(),MANAGER_READ.name())
                        .requestMatchers(POST,"/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(),MANAGER_CREATE.name())
                        .requestMatchers(PUT,"/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(),MANAGER_UPDATE.name())
                        .requestMatchers(DELETE,"/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(),MANAGER_DELETE.name())

                        /*.requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(GET,"/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(POST,"/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT,"/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE,"/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())*/

                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
