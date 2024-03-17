package note.javadeveloper.service;

import note.javadeveloper.dto.UserDto;
import note.javadeveloper.entity.Role;
import note.javadeveloper.entity.UserEntity;
import note.javadeveloper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private UserRepository userRepository;

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserDto userDto) {
        userRepository.save(UserEntity.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .role(Role.ROLE_USER)
                .build());
    }

}
