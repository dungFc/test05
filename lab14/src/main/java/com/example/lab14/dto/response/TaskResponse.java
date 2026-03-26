package com.example.lab14.dto.response;
import com.example.lab14.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskResponse {
    private Integer id;

    private String title;

    private String description;

    private LocalDateTime deadline;

    private TaskStatus status;

    private Integer projectId;

    private String projectName;

    private Integer assignedUserId;

    private String assignedUsername;

    private Boolean active;

    private LocalDateTime createdAt;

}

