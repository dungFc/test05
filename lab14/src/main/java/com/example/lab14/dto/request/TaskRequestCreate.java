package com.example.lab14.dto.request;

import com.example.lab14.entity.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskRequestCreate {
    @NotBlank(message = "Title không được để trống")
    @Size(max = 100, message = "Title tối đa 100 ký tự")
    private String title;

    @Size(max = 500, message = "Description tối đa 500 ký tự")
    private String description;

    @FutureOrPresent(message = "Deadline phải ở hiện tại hoặc tương lai")
    private LocalDateTime deadline;

    private TaskStatus status;

    @NotNull(message = "ProjectId không được null")
    private Integer projectId;

    private Integer assignedUserId;

    private Boolean active;


}

