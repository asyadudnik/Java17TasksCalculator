package com.tasks.calculator.services;

import com.tasks.calculator.dto.Task;
import com.tasks.calculator.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("taskService")
@Transactional
public class TaskService {
    private final TaskRepository repo;

    @Autowired
    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public List<Task> findAll() {
        List<Task> users = new ArrayList<>();
        this.repo.findAll().forEach(users::add);
        return users;
    }

}
