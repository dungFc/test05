package com.example.tuan3.dto.respone;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer createdById;
}