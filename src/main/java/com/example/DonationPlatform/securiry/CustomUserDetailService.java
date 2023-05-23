package com.example.DonationPlatform.securiry;

import com.example.DonationPlatform.domain.daouser.DaoUserWithAllInfo;
import com.example.DonationPlatform.repository.IuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final IuserRepository userRepository;

    @Autowired
    public CustomUserDetailService(IuserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DaoUserWithAllInfo user = userRepository.findByLogin(username);
        Optional<DaoUserWithAllInfo> optionalOfUser = Optional.ofNullable(user);
        if (optionalOfUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails securityUser = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

        return securityUser;
    }
}