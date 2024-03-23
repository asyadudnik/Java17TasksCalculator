package com.tasks.calculator.repositories;

import com.tasks.calculator.dto.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Override
    List<Task> findAll(Sort sort);

    @Override
    Page<Task> findAll(Pageable pageable);

    List<Task> findAll(Task task);

    Optional<Task> findByTaskName(String taskName);
    Optional<Task> findByTaskNumber(Long taskNumber);
}
