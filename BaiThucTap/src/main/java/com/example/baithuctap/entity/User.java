package com.example.baithuctap.entity;






import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
public class User {
    private int id;
    private String username;
    private String email;
    private List<Task> tasks;

    public User(int id, String username, String email) {
        if(username == null || username.isEmpty()) throw new IllegalArgumentException("Username không được null");
        if(email == null || !email.contains("@")) throw new IllegalArgumentException("Email không hợp lệ");
        this.id = id;
        this.username = username;
        this.email = email;
        this.tasks = new ArrayList<>();
    }

    public void assignTask(Task task) {
        if(task == null) throw new IllegalArgumentException("Task null");
        if(!tasks.contains(task)) {
            tasks.add(task);
            task.setAssignee(this);
        }
    }

    public void removeTask(Task task) {
        if(task == null) return;
        tasks.remove(task);
        task.setAssignee(null);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", email='" + email + '\'' + '}';
    }
}