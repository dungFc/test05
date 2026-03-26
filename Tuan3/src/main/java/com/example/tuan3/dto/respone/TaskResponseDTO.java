package com.example.tuan3.dto.respone;

import com.example.tuan3.entity.TaskStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private TaskStatus status;
    private Integer projectId;
    private Integer assignedUserId;
}