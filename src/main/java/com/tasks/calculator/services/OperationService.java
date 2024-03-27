package com.tasks.calculator.services;

import com.tasks.calculator.dto.Operation;
import com.tasks.calculator.dto.Task;
import com.tasks.calculator.repositories.OperationRepository;
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
@Service
public class OperationService {
    private final OperationRepository repo;

    @Autowired
    public OperationService(OperationRepository repo) {
        this.repo = repo;
    }

    public List<Operation> listAll() {
        List<Operation> operations = new ArrayList<>();
         this.repo
                .findAll()
                .forEach(operations::add);
        return operations;
    }
    public Operation save (Task task, Operation operation)
    {
        if (operation != null) {
            if (log.isDebugEnabled()) {
                log.info(JsonUtils.toJson(operation));
            }
        } else {
            log.error("Operation not filled.");
            return null;
        }
        Optional<Operation> operationOptional = this.repo.findByTaskNameAndOperationName(
                task.getTaskName(), operation.getOperationName()    );
        if (operationOptional.isPresent()) {
            log.info("Saving of operation = {}", operationOptional);
            if (operation.equals(operationOptional.get())) {
                return operationOptional.get();
            } else {
                log.info("Operation {} already exist", operation.getOperationName());

                Operation realOperation = operationOptional.get();
                Operation updated = Operation.builder()
                        .id(realOperation.getId())
                        .operationNumber(realOperation.getOperationNumber())
                        .taskName(realOperation.getTaskName())
                        .operationDescription(realOperation.getOperationDescription())
                        .operationStatus(operation.getOperationStatus())
                        .operationPrice(realOperation.getOperationPrice())
                        .build();
                return this.repo.save(updated);
            }
        } else {
            return this.repo.save(operation);
        }

    }
    private boolean existsById(Long id) {
        return this.repo.existsById(id);
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
