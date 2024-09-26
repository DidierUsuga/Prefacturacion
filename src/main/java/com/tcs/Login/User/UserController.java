package com.tcs.Login.User;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/buscar-id/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id){
        UserDTO userDTO = userService.getUser(id);
        if(userDTO==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }


    @GetMapping(value = "/traer-usuarios")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }



    @PutMapping(value = "/actualizar/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest){
        userRequest.setId(id);
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Integer id) {
        UserResponse response = userService.deleteUser(id);
        if ("El usuario no se encontró".equals(response.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
