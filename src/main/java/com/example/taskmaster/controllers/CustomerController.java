package com.example.taskmaster.controllers;

import com.example.taskmaster.models.Customer;
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
        Customer newCustomer = new Customer();
        newCustomer.setName( customer.getName());
        newCustomer.setOld( customer.getOld());
        customerRepository.save(newCustomer);
        return newCustomer;
    }


}