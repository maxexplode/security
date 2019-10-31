package com.unipoint.security;

import com.unipoint.security.util.AuthBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Configuration {
    @Bean
    public AuthBuilder authBuilder()
    {
        return AuthBuilder.builder().firebaseConfig("unipoint.json")
                .firebaseDb("https://unipointconsumerapp.firebaseio.com")
                .authPath("/api/**")
                .tokenHeader("X-Token")
                .build();
    }
}
