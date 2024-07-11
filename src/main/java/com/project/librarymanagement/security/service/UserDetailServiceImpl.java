package com.project.librarymanagement.security.service;

import com.project.librarymanagement.entity.user.User;
import com.project.librarymanagement.service.helper.MethodHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl  implements UserDetailsService {
    @Autowired
    private final MethodHelper methodHelper;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = methodHelper.loadUserByEmail(email);
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getPassword(),
                authorities,
                user.getActiveRole()//to be able to switch between roles
        );
    }
}
