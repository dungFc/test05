package com.example.tuan3.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDTO {
    @NotBlank(message = "Project name không được để trống")
    private String name;

    private String description;
}