package com.tcs.Login.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {

        User user = User.builder()
                .id(userRequest.id)
                .role(userRequest.getRole())
                .name(userRequest.getName())
                .build();

        userRepository.updateUser(user.id, user.role, user.name);

        return new UserResponse("El usuario se registró satisfactoriamente");
    }

    public UserDTO getUser(Integer id) {
        User user= userRepository.findById(id).orElse(null);

        if (user!=null)
        {
            UserDTO userDTO = UserDTO.builder()
                    .id(user.id)
                    .username(user.username)
                    .role(user.role)
                    .name(user.name)
                    .build();
            return userDTO;
        }
        return null;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .name(user.getName())
                .build()).collect(Collectors.toList());
    }

    @Transactional
    public UserResponse deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return new UserResponse("El usuario se eliminó satisfactoriamente");
        } else {
            return new UserResponse("El usuario no se encontró");
        }
    }
}