// src/main/java/com/example/taskmanager/controller/TaskController.java
package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.security.CustomUserDetails;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public Task createTask(@RequestBody Task task, @AuthenticationPrincipal CustomUserDetails userDetails) {
        task.setUser(userDetails.getUser());
        return taskService.createTask(task);
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam(required = false) String status, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (status == null) {
            return taskService.getTasksByUserId(userDetails.getUser().getId());
        }
        return taskService.getTasksByStatusAndUserId(status, userDetails.getUser().getId());
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        task.setId(id);
        return taskService.updateTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}