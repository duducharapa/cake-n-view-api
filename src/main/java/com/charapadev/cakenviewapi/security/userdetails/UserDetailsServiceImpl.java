package com.charapadev.cakenviewapi.security.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.charapadev.cakenviewapi.modules.users.User;
import com.charapadev.cakenviewapi.modules.users.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFound = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Credentials not match"));

        return new CustomUserDetails(userFound);
    }

}
