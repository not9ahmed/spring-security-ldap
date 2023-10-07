package com.notahmed.springsecurityldap.controller.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.server.UnboundIdContainer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {




    // TODO read more about EmbeddedLdapServerContextSourceFactoryBean
    // configuration for the ldap
    // setting up the ldap server configs
    @Bean
    public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
        EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean =
                EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();


        contextSourceFactoryBean.setPort(0);


        return contextSourceFactoryBean;
    }



    // TODO read more about ldapAuthenticationManager
    @Bean
    public AuthenticationManager ldapAuthenticationManager(BaseLdapPathContextSource contextSource) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);


        factory.setUserDnPatterns("uid={0},ou=people");
        factory.setUserSearchBase("");
//        factory.setUserDetailsContextMapper(new PersonContextMapper());
        return factory.createAuthenticationManager();
    }







    // authorization which is found in all projects I have worked with
    // builder pattern
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        // all request should be all authenticated
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().fullyAuthenticated()
                ).formLogin(Customizer.withDefaults());

        return http.build();
    }







}
