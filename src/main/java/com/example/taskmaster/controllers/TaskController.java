package com.example.taskmaster.controllers;

import com.example.taskmaster.models.Task;
import com.example.taskmaster.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class TaskController
{

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List) taskRepository.findAll();
    }

    @GetMapping("/users/{assignee}/tasks")
    public Optional<Task> getTasks(@PathVariable String assignee)
    {
        Optional<Task> taskList = taskRepository.findByAssignee(assignee);
        return taskList;
    }

    @PostMapping("/tasks")
    public Task addNewTask (@RequestBody Task task)
    {
        Task newTask = new Task(task.getId(), task.getTitle(), task.getDescription(), " - Available", "none");
        historySetter(newTask);
        taskRepository.save(newTask);
        return newTask;
    }

    @PutMapping("/tasks/{id}/state")
    public Task updateTaskStatus(@PathVariable String id)
    {
        Task taskToBeUpdated = taskRepository.findById(id).get();
        if (taskToBeUpdated.getStatus().equals(" - Assigned"))
        {
            taskToBeUpdated.setStatus(" - Accepted");
            historySetter(taskToBeUpdated);
        }
        else if (taskToBeUpdated.getStatus().equals(" - Accepted"))
        {
            taskToBeUpdated.setStatus(" - Finished");
            historySetter(taskToBeUpdated);
        }
        taskRepository.save(taskToBeUpdated);
        return taskToBeUpdated;
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public Task addTaskAssignee(@PathVariable String id, @PathVariable String assignee) {
        Task taskAssignee = taskRepository.findById(id).get();
        taskAssignee.setAssignee(assignee);
        taskAssignee.setStatus(" - Assigned");
        historySetter(taskAssignee);
        taskRepository.save(taskAssignee);
        return taskAssignee;
    }

    @DeleteMapping("/tasks/{id}/delete")
    public Task deleteTaskStatus(@PathVariable String id)
    {
        Task taskToBeDeleted = taskRepository.findById(id).get();
        taskRepository.delete(taskToBeDeleted);
        return taskToBeDeleted;
    }

    // Helper Method
    private void historySetter(Task task)
    {
        Task.History history = new Task.History(new Date().toString(), task.getStatus());
        task.addHistory(history);
    }
}
