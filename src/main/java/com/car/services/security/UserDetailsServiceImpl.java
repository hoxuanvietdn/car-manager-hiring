package com.car.services.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.car.mysql.models.Permission;
import com.car.mysql.models.User;
import com.car.mysql.repositories.MysqlUserRepository;
import com.car.services.UserService;

/**
 * Created by hxviet on 6/9/2015.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private MysqlUserRepository mysqlUserRepository;

    @Autowired
    private UserService userService;

    @Override
    public UserDetailsImpl loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = mysqlUserRepository.findByUserName(userName);

        if (user == null) {
            user = mysqlUserRepository.findByUserEmail(userName);
            if (user == null) {
                throw new UsernameNotFoundException("User not found!");
            }
        }

        Collection<Permission> permissions = userService.getPermissionsByUser(user);

        if (permissions == null || permissions.isEmpty()) {
            return null;
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Permission permission : permissions) {
            SimpleGrantedAuthority authority1 = new SimpleGrantedAuthority(permission.getPermissionName());
            authorities.add(authority1);
        }

        UserDetailsImpl userDetail = new UserDetailsImpl(user.getUserName(), user.getPassword(),
                authorities, user.isEnable(), user.getThemeColor());

        return userDetail;
    }
}