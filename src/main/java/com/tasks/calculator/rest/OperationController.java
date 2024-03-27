package com.tasks.calculator.rest;

import com.tasks.calculator.dto.Operation;
import com.tasks.calculator.dto.Task;
import com.tasks.calculator.services.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Controller
@RequestMapping("/rest/api/tasks/operations")
public class OperationController {
    private final OperationService operationService;
    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping(value="/", produces = {APPLICATION_JSON_VALUE})
    public String listAll(Model model) {
        List<Operation> operationList = this.operationService.listAll();
        model.addAttribute("listOperations", operationList);
        return "/operations/operationList";
    }

    @GetMapping(value="/new", produces = {APPLICATION_JSON_VALUE})
    public String showNewOperationPage(Model model) {
        Operation operation = new Operation();
        model.addAttribute("operation", operation);
        return "/operations/new_operation";
    }

    @PostMapping(value = "/save", produces = {APPLICATION_JSON_VALUE})
    public String saveOperation(@ModelAttribute("task") Task task, @ModelAttribute("operation") Operation operation) {
        this.operationService.save(task, operation);
        return "/operations/edit_operation";
    }
    @DeleteMapping(value = "/delete/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String deleteTask(@PathVariable(name = "id") Long id) {
        this.operationService.delete(id);
        return "redirect:/operations/operationsList";
    }

}
