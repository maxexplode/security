package com.unipoint.security.util;

import lombok.Builder;
import lombok.Data;

import java.net.URL;

@Data
@Builder
public class AuthBuilder {
    private String firebaseConfig;
    private String firebaseDb;
    private String authPath;
    private String tokenHeader;
    private URL configPath;
}
