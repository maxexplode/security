package com.unipoint.security.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutionException;

import com.unipoint.security.config.SecurityProperties;
import com.unipoint.security.jwt.FirebaseAuthenticationToken;
import com.unipoint.security.jwt.FirebaseUserDetails;
import com.unipoint.security.util.AuthBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Component
public class FirebaseAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final AuthBuilder authBuilder;
    @Autowired
    private FirebaseAuth firebaseAuth;

    public FirebaseAuthenticationProvider(AuthBuilder authBuilder) {
        this.authBuilder = authBuilder;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        final FirebaseAuthenticationToken authenticationToken = (FirebaseAuthenticationToken) authentication;

        ApiFuture<FirebaseToken> task = firebaseAuth.verifyIdTokenAsync(authenticationToken.getToken());
        try {
            return appendAuthRoles(task.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new SessionAuthenticationException(e.getMessage());
        }
    }

    private FirebaseUserDetails appendAuthRoles(FirebaseToken firebaseToken) {

        FirebaseUserDetails firebaseUserDetails = new FirebaseUserDetails(firebaseToken.getEmail(), firebaseToken.getUid());

        try (Connection connection = DriverManager.getConnection(SecurityProperties.jdbcUrl(), SecurityProperties.username(), SecurityProperties.password())) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select role.role_name from user_role, role, user_role_role where\n" +
                    "                                                    user_role.id = user_role_role.user_role_id\n" +
                    "                                                    and role.id = user_role_role.role_id and username = ?")) {
                preparedStatement.setString(1, firebaseToken.getEmail());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        firebaseUserDetails.getAuthorities().add(new SimpleGrantedAuthority(resultSet.getString("role_name")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e){
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return firebaseUserDetails;
    }
}