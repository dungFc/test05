package com.example.tuan3.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestDTO {

    @NotBlank(message = "Title không được để trống")
    private String title;

    private String description;

    @Future(message = "Deadline phải lớn hơn thời gian hiện tại")
    private LocalDateTime deadline;

    @NotNull(message = "ProjectId không được để trống")
    private Integer projectId;

    private Integer assignedUserId; // Có thể null nếu chưa assign
}