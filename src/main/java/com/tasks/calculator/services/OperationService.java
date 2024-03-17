package com.tasks.calculator.services;

import com.tasks.calculator.entities.Task;
import com.tasks.calculator.repositories.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OperationService {
    private final OperationRepository repo;

    @Autowired
    public OperationService(OperationRepository repo) {
        this.repo = repo;
    }

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        this.repo
                .findAll()
                .forEach(t->log.info(t.getTaskName()));
        return tasks;
    }

}
