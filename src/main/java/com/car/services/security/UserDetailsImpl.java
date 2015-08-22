package com.car.services.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by hxviet on 6/9/2015.
 */
public class UserDetailsImpl implements UserDetails{
	private static final long serialVersionUID = -2343837282060922921L;
	
	private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean isEnable;
    private String themeColor;
    public UserDetailsImpl(String userName, String password, List<GrantedAuthority> authorities, boolean isEnable, String themeColor){
        this.username = userName;
        this.password = password;
        this.authorities = authorities;
        this.isEnable = isEnable;
        this.themeColor = themeColor;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public String getThemeColor() {
        return themeColor;
    }
    public boolean isAccountNonExpired() {
        return true;
    }
    public boolean isAccountNonLocked() {
        return true;
    }
    public boolean isCredentialsNonExpired() {
        return true;
    }
    public boolean isEnabled() {
        return isEnable;
    }
    
	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof UserDetailsImpl) {
			return username.equals(((UserDetailsImpl) rhs).username);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}
}