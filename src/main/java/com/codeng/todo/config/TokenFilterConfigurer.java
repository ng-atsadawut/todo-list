package com.codeng.todo.config;

import com.codeng.todo.filter.TokenFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    private final TokenFilter tokenFilter;

    public TokenFilterConfigurer(TokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
