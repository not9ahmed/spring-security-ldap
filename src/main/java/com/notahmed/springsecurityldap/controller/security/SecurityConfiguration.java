package com.notahmed.springsecurityldap.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {



    // TODO read more about EmbeddedLdapServerContextSourceFactoryBean
    // TODO read this link https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/ldap.html
    // configuration for the ldap
    // setting up the ldap server configs




    // authorization which is found in all projects I have worked with
    // builder pattern
    // authentication happens here and specifying the login page
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        // all request should be all authenticated
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().fullyAuthenticated()
                ).formLogin(Customizer.withDefaults());

        return http.build();
    }



    // TODO read more about ldapAuthenticationManager

    // this is still old syntax and will be deprecated
    // setting up the ldap authentication
    // same as the old version in the video
    // so running the project will run an embedded ldap server and the spring boot project

    // only people within the ou of people can log in
    // which is bob
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");

    }

    // changing data in the ldif fixed the issue



}
