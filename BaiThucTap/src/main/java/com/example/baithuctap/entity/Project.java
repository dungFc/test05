package com.example.baithuctap.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Project {
    private int id;
    private String name;
    private String description;
    private List<Task> tasks;

    public Project(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        if(task == null) throw new IllegalArgumentException("Task null");
        if(tasks.stream().anyMatch(t -> t.getId() == task.getId()))
            throw new IllegalArgumentException("Task trùng ID");
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name='" + name + '\'' + ", tasks=" + tasks.size() + '}';
    }
}
