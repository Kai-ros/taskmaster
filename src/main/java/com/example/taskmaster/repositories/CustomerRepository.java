package com.example.taskmaster.repositories;

import com.example.taskmaster.models.Customer;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface CustomerRepository extends CrudRepository<Customer, String> {
    Optional<Customer> findById(String id);
}