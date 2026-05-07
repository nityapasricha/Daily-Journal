package org.techm.samples.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.techm.samples.entity.User;
import org.techm.samples.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));;
        if (user == null) throw new UsernameNotFoundException("User not found");

        String springRole = "ROLE_" + user.getRole().toUpperCase(); 
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(springRole));

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPass(),
            authorities
        );
    }
}
