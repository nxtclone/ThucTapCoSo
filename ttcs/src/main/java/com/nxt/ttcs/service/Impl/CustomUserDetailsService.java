package com.nxt.ttcs.service.Impl;

import com.nxt.ttcs.entity.CustomUserDetail;
import com.nxt.ttcs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetail user=userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username not found!");
        } else {
            return new User(user.getUsername(),user.getPassword(), user.getAuthorities());
        }
    }
}
