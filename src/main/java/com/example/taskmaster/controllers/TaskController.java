package com.example.taskmaster.controllers;

import com.example.taskmaster.models.Task;
import com.example.taskmaster.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class TaskController
{

    @Autowired
    TaskRepository taskRepository;

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
        Task newTask = new Task();
        newTask.setTitle( task.getTitle() );
        newTask.setDescription( task.getDescription() );

        if (task.getAssignee() == null)
        {
            newTask.setStatus("Available");
        }
        else
        {
            newTask.setAssignee( task.getAssignee() );
            newTask.setStatus("Assigned");
        }

        taskRepository.save(newTask);
        return newTask;
    }

    /*
    * A user should be able to make a PUT request to /tasks/{id}/state to advance the status of that task.
    Tasks should advance from Available -> Assigned -> Accepted -> Finished.
    As the task advances, add an entry to the history array to note the advancement. Include the date and the action that was taken.
    */

    @PutMapping("/tasks/{id}/state")
    public Task updateTaskStatus(@PathVariable String id)
    {
        Task taskToBeUpdated = taskRepository.findById(id).get();

        if (taskToBeUpdated.getStatus().equals("Available"))
        {
            taskToBeUpdated.setStatus("Assigned");
            taskToBeUpdated.setHistory(" - Changed status to Assigned.");
        }
        else if ( taskToBeUpdated.getStatus().equals("Assigned"))
        {
            taskToBeUpdated.setStatus("Accepted");
            taskToBeUpdated.setHistory(" - Changed status to Accepted.");
        }
        else if ( taskToBeUpdated.getStatus().equals("Accepted"))
        {
            taskToBeUpdated.setStatus("Finished");
            taskToBeUpdated.setHistory(" - Changed status to Finished.");
        }
        taskRepository.save(taskToBeUpdated);
        return taskToBeUpdated;
    }
}
