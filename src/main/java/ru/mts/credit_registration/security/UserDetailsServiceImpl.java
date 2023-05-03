package ru.mts.credit_registration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mts.credit_registration.entity.UserEntity;
import ru.mts.credit_registration.serivce.impl.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userIntoDatabase = userService.getUserByEmail(email);
        return new UserDetailsImpl(userIntoDatabase);
    }

    public int enableUser(Long userId) {
        return userService.enableUser(userId);
    }
}
