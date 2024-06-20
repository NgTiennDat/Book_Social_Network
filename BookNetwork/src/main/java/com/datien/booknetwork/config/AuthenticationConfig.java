package com.datien.booknetwork.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuthenticationConfig implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        return Optional.empty();
    }
}
