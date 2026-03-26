package com.example.baithuctap;

import com.example.baithuctap.entity.Project;
import com.example.baithuctap.entity.Task;
import com.example.baithuctap.entity.TaskStatus;
import com.example.baithuctap.entity.User;

public class Main {
    public static void main(String[] args) {
        try {
            User u1 = new User(1, "khôi", "khoi@gmail.com");
            User u2 = new User(2, "linh", "linh@gmail.com");

            Task t1 = new Task(1, "Code OOP", "Viết class Java", TaskStatus.TODO);
            Task t2 = new Task(2, "Test logic", "Test console", TaskStatus.IN_PROGRESS);

            Project p = new Project(1, "Project A", "Dự án demo");
            p.addTask(t1);
            p.addTask(t2);

            u1.assignTask(t1);
            u2.assignTask(t2);

            System.out.println(p);
            System.out.println(u1);
            System.out.println(u1.getTasks());
            System.out.println(u2.getTasks());

            t1.setStatus(TaskStatus.DONE);

            System.out.println("Sau khi đổi trạng thái:");
            System.out.println(t1);

        } catch (Exception e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}
