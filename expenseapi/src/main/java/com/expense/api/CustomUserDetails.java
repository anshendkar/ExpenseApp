package com.expense.api;

import com.expense.api.entity.Userdata;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final Userdata userdata;

    public CustomUserDetails(Userdata userdata) {
        this.userdata = userdata;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return userdata.getPassword();
    }

    @Override
    public String getUsername() {
        return userdata.getEmail();
    }
}
