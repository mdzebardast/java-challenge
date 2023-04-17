package jp.co.axa.apidemo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class InMemoryUserDetailsService implements UserDetailsService {


    private final Map<String, InMemoryUserDetails> users = new HashMap<>();

    public InMemoryUserDetailsService() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        users.put("user", new InMemoryUserDetails("user", passwordEncoder.encode("password"), authorities));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InMemoryUserDetails userDetails = users.get(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return userDetails;
    }
}
