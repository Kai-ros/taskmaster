package com.example.taskmaster.controllers;

import com.example.taskmaster.models.Customer;
import com.example.taskmaster.models.Task;
import com.example.taskmaster.repositories.CustomerRepository;
import com.example.taskmaster.repositories.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return (List) customerRepository.findAll();
    }

    @PostMapping("/customers")
    public Customer addNewUser (@RequestBody Customer customer) {
        Customer c = new Customer();
        c.setName( customer.getName() );
        c.setOld( customer.getOld() );
        customerRepository.save(c);
        return c;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List) taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task addNewTask (@RequestBody Task task)
    {
        Task newTask = new Task();
        newTask.setTitle( task.getTitle() );
        newTask.setDescription( task.getDescription() );
        newTask.setStatus("Available");
        taskRepository.save(newTask);
        return newTask;
    }

    @PutMapping("/tasks/{id}/state")
    public Task updateTaskStatus(@PathVariable String id)
    {
        Task newTask = taskRepository.findById(id).get();
        if (newTask.getStatus().equals("Available"))
        {
            newTask.setStatus("Assigned");
        } else if ( newTask.getStatus().equals("Assigned"))
        {
            newTask.setStatus("Accepted");

        } else if ( newTask.getStatus().equals("Accepted"))
        {
            newTask.setStatus("Finished");
        }
        taskRepository.save(newTask);
        return newTask;
    }
}