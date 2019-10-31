package com.unipoint.security.config;


import java.io.*;
import java.util.Properties;

public class SecurityProperties {
    private static Properties properties;

    static {
        File serverConf = new File(SecurityProperties.class.getClassLoader().getResource("security.properties").getPath().replaceFirst("/", ""));
        if (serverConf.exists()) {
            try (InputStream stream = new FileInputStream(serverConf);) {
                properties = new Properties();
                properties.load(stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String jdbcUrl()
    {
        return properties.getProperty("authz.jdbc.url", null);
    }

    public static String username()
    {
        return properties.getProperty("authz.jdbc.username", "postgres");
    }

    public static String password()
    {
        return properties.getProperty("authz.jdbc.password", "root");
    }
}
