package com.tasks.calculator.repositories;

import com.tasks.calculator.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("operationRepository")
public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Override
    List<Operation> findAll(Sort sort);

    @Override
    Page<Operation> findAll(Pageable pageable);

    List<Operation> findAll(Operation operation);
}
