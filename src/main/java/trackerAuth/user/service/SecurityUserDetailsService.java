package trackerAuth.user.service;

import trackerAuth.user.dto.SecurityUserDetails;
import trackerAuth.user.validation.exception.UserNotFoundException;
import trackerAuth.user.dto.UserDtoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public SecurityUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Requested to loadUserByUsername() with id = {}", username);
        UserDtoResponse userDtoResponse;
        try {
            userDtoResponse = userService.findByUserName(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("user with username " + username + " was not found");
        }
        log.debug("Loaded UserDtoResponse {}", userDtoResponse);
        return new SecurityUserDetails(userDtoResponse);
    }
}
