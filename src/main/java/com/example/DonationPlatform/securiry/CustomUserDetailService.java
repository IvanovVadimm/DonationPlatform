package com.example.DonationPlatform.securiry;

import com.example.DonationPlatform.domain.User;
import com.example.DonationPlatform.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {


    private IUserRepository userRepository;

    @Autowired
    public CustomUserDetailService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username)); //TODO: написать свои ошибки Exeptions

        UserDetails securityUser = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

        return securityUser;
    }
}
