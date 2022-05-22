package com.fabienit.biblioweb.security;

import com.fabienit.biblioweb.model.beans.RegisteredUserBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * UserPrincipal
 */
public class UserPrincipal implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 6751582592453987096L;

    private RegisteredUserBean registeredUserBean;

    public UserPrincipal(RegisteredUserBean registeredUserBean) {
        this.registeredUserBean = registeredUserBean;
    }

    public RegisteredUserBean getUserPrincipal() {
        return this.registeredUserBean;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // Init new GrantedAuthority list
        List<GrantedAuthority> authorities = new ArrayList<>();

        // add registeredUserBean role into the list, there is no loop cause only one role
        authorities.add(new SimpleGrantedAuthority("ROLE_" + registeredUserBean.getRoles()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.registeredUserBean.getPassword();
    }

    @Override
    public String getUsername() {

        // Set email as username for login
        return this.registeredUserBean.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    
}