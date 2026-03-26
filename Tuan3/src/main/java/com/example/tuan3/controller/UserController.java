package com.example.tuan3.controller;

import com.example.tuan3.dto.request.UserRequestDTO;
import com.example.tuan3.dto.respone.UserResponseDTO;
import com.example.tuan3.exception.ApiResponse;
import com.example.tuan3.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request){
        UserResponseDTO user = userService.createUser(request);
        return new ApiResponse<>(200, "User created successfully", user);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> getUser(@PathVariable int id){
        UserResponseDTO user = userService.getUserById(id);
        return new ApiResponse<>(200, "User retrieved successfully", user);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponseDTO> updateUser(@PathVariable int id, @Valid @RequestBody UserRequestDTO request){
        UserResponseDTO user = userService.updateUser(id, request);
        return new ApiResponse<>(200, "User updated successfully", user);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return new ApiResponse<>(200, "User deleted successfully", null);
    }

    @GetMapping
    public ApiResponse<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ApiResponse<>(200, "Users retrieved successfully", users);
    }
}