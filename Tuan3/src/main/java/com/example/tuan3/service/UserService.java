package com.example.tuan3.service;

import com.example.tuan3.dto.respone.UserResponseDTO;
import com.example.tuan3.dto.request.UserRequestDTO;
import com.example.tuan3.entity.User;
import com.example.tuan3.repository.UserRepo;
import com.example.tuan3.exception.CustomException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto){
        if(userRepo.existsByUsername(dto.getUsername()))
            throw new CustomException("Username đã tồn tại");
        if(userRepo.existsByEmail(dto.getEmail()))
            throw new CustomException("Email đã tồn tại");

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword()) // TODO: hash password
                .isActive(true)
                .build();

        user = userRepo.save(user);

        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    public UserResponseDTO getUserById(int id){
        User user = userRepo.findById(id)
                .orElseThrow(() -> new CustomException("User không tồn tại"));
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    @Transactional
    public UserResponseDTO updateUser(int id, UserRequestDTO dto){
        User user = userRepo.findById(id)
                .orElseThrow(() -> new CustomException("User không tồn tại"));

        if(!user.getUsername().equals(dto.getUsername()) && userRepo.existsByUsername(dto.getUsername()))
            throw new CustomException("Username đã tồn tại");
        if(!user.getEmail().equals(dto.getEmail()) && userRepo.existsByEmail(dto.getEmail()))
            throw new CustomException("Email đã tồn tại");

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // TODO: hash password

        user = userRepo.save(user);

        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    @Transactional
    public void deleteUser(int id){
        User user = userRepo.findById(id)
                .orElseThrow(() -> new CustomException("User không tồn tại"));
        userRepo.delete(user);
    }

    public List<UserResponseDTO> getAllUsers(){
        return userRepo.findAll().stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getUsername(), u.getEmail()))
                .collect(Collectors.toList());
    }
}