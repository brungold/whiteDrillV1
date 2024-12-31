package pl.whitedrillv1.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import pl.whitedrillv1.domain.usercrud.User;
import pl.whitedrillv1.domain.usercrud.UserRepository;

import java.util.List;


@Log4j2
@AllArgsConstructor
class UserDetailsServiceImpl implements UserDetailsManager{

    public static final String DEFAULT_USER_ROLE = "ROLE_USER";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new RuntimeException("not fount user"));
    }

    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())){
            log.warn("not saved user - already exists");
            throw new RuntimeException("not saved user - already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User createdUser = new User(
                user.getUsername(),
                encodedPassword,
                true,
                List.of(DEFAULT_USER_ROLE)
        );
        User savedUser = userRepository.save(createdUser);
        log.info("Saved user with Id: " + savedUser.getId());
        // send email confirmation (wtedy enabled na false, mail z tokenem i potwierdzenie)
    }

    @Override
    public void updateUser(final UserDetails user) {

    }

    @Override
    public void deleteUser(final String username) {

    }

    @Override
    public void changePassword(final String oldPassword, final String newPassword) {

    }

    @Override
    public boolean userExists(final String username) {
        return false;
    }
}
