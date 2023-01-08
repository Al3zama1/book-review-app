package com.example.bookreviewapp.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        HashSet<GrantedAuthority> authorities = extractAuthorities(jwt);

        return new JwtAuthenticationToken(jwt, authorities);
    }

    private HashSet<GrantedAuthority> extractAuthorities(Jwt jwt) {
        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (String role : getRoles(jwt)) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return grantedAuthorities;
    }

    private HashSet<String> getRoles(Jwt jwt) {
        return (HashSet<String>) jwt.getClaimAsMap("realm_access").get("roles");
    }
}
