package com.unipoint.security;

import com.unipoint.security.util.AuthBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class Configuration {
    @Bean
    public AuthBuilder authBuilder()
    {
        return AuthBuilder.builder()
                .firebaseDb("https://unipointconsumerapp.firebaseio.com")
                .authPath("/**")
                .configPath(getClass().getClassLoader().getResource("unipoint"))
                .tokenHeader("X-Token")
                .build();
    }
}
