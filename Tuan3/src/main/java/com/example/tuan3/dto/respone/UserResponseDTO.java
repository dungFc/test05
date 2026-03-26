package com.example.tuan3.dto.respone;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private int id;           // user id
    private String username;  // tên đăng nhập
    private String email;
}
