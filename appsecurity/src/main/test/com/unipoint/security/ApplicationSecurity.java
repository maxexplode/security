package com.unipoint.security;

import com.unipoint.security.util.AuthBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Configuration.class, WebSecurityProvider.class})
public class ApplicationSecurity {

    @Test
    public void testSecurityContext()
    {

    }
}
