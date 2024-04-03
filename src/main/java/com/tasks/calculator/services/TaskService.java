package com.tasks.calculator.services;

import com.tasks.calculator.dto.Task;
import com.tasks.calculator.dto.User;
import com.tasks.calculator.repositories.TaskRepository;
import com.tasks.calculator.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public Task save(Task task) {
        if (task != null) {
            if (log.isDebugEnabled()) {
                System.out.println(JsonUtils.toJson(task));
            }
        } else {
            log.error("Task not filled.");
            return null;
        }
        Optional<Task> taskOptional = this.repo.findByTaskName(task.getTaskName());
        if (taskOptional.isPresent()) {
            System.out.println("Saving of user = {}"+ taskOptional);
            if (task.equals(taskOptional.get())) {
                return taskOptional.get();
            } else {
                System.out.println("User {} already exist"+ task.getTaskName());

                Task realTask = taskOptional.get();
                Task updated = Task.builder()
                        .id(realTask.getId())
                        .taskNumber(realTask.getTaskNumber())
                        .taskName(realTask.getTaskName())
                        .taskDescription(realTask.getTaskDescription())
                        .taskStatus(realTask.getTaskStatus())
                        .taskCost(realTask.getTaskCost())
                        .operationsCount(realTask.getOperationsCount())
                        .build();
                return this.repo.save(updated);
            }
        } else {
            return this.repo.save(task);
        }

    }

    private boolean existsById(Long id) {
        return this.repo.existsById(id);
    }
    public Task get(Long id) {
        Optional<Task> value = this.repo.findById(id);
        if (value.isPresent()) {
            return value.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public void delete(long id) {
        if (!existsById(id)) {
            throw new NoSuchElementException();
        } else {
            this.repo.deleteById(id);
        }
    }
}
