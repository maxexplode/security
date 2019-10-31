package com.unipoint.security.jwt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class FirebaseUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;

	private final boolean enabled = true;
	private final boolean credentialsNonExpired = true;
	private final boolean accountNonLocked = true;
	private final boolean accountNonExpired = true;
	private final String password = null;
	private String username = null;
	private String id = null;
	Collection<SimpleGrantedAuthority> grantedAuthorities;

	public FirebaseUserDetails(String email, String uid) {
		this.username = email;
		this.id = uid;
	}

	@Override
	public Collection<SimpleGrantedAuthority> getAuthorities() {
		if(grantedAuthorities==null)
		{
			grantedAuthorities = new ArrayList<>();
		}
		return grantedAuthorities;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public String getPassword() {
		return password;
	}

}