package com.unipoint.security;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.net.URL;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.unipoint.security.TestConstants.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Configuration.class, WebSecurityProvider.class})
@Import(WebSecurityProvider.class)
@ComponentScan("com.unipoint.security.controller")
@AutoConfigureMockMvc
public class ApplicationSecurity {

    @Autowired
    private MockMvc mockMvc;

    private String firebaseToken = null;

    @Before
    public void initialize() throws Exception {
        URL token = getClass().getClassLoader().getResource("token");
        firebaseToken = IOUtils.toString(token, "UTF-8");
    }

    @Test
    public void testSecurityContext() throws Exception {
        Assert.assertNotNull("Token cannot be null", firebaseToken);
        this.mockMvc.perform(get("/secured").header("X-Token", firebaseToken)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(WORKING)));
    }

    @Test
    public void testSecurityContextRoles() throws Exception
    {
        Assert.assertNotNull("Token cannot be null", firebaseToken);
        this.mockMvc.perform(get("/secured_1").header("X-Token", firebaseToken)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(WORKING)));
    }

    @Test
    public void testSecurityContextRoles_PreAuthorize() throws Exception
    {
        Assert.assertNotNull("Token cannot be null", firebaseToken);
        this.mockMvc.perform(get("/secured_2").header("X-Token", firebaseToken)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(WORKING)));
    }
}
