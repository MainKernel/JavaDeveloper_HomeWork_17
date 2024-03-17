package note.javadeveloper.service;

import note.javadeveloper.dto.UserDto;
import note.javadeveloper.entity.UserEntity;
import note.javadeveloper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with this username"));
        List<GrantedAuthority> grantedAuthorities = List.of((GrantedAuthority) () -> user.getRole().name());
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public UserDto findUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .email(userEntity.getEmail())
                .isEnabled(userEntity.isEnabled())
                .build();
    }
}
