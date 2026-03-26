package com.example.baithuctap.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task {
    private int id;
    private String title;
    private String description;
    private TaskStatus status;
    private User assignee;

    public Task(int id, String title, String description, TaskStatus status) {
        if(title == null || title.isEmpty()) throw new IllegalArgumentException("Title không được null");
        if(status == null) throw new IllegalArgumentException("Status không hợp lệ");
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public void setStatus(TaskStatus status) {
        if(status == null) throw new IllegalArgumentException("Status null");
        this.status = status;
    }

    public void setAssignee(User user) {
        this.assignee = user;
    }

    public User getAssignee() {
        return assignee;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", title='" + title + '\'' + ", status=" + status + ", assignee=" + (assignee != null ? assignee.getTasks().size() : "null") + '}';
    }
}