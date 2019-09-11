package com.example.taskmaster.repositories;

import com.example.taskmaster.models.Customer;
import com.example.taskmaster.models.Task;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface TaskRepository extends CrudRepository<Task, String>
{
    Optional<Task> findById(String id);
    Optional<Task> findByAssignee(String assignee);
}