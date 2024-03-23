package com.tasks.calculator.rest;

import com.tasks.calculator.dto.Operation;
import com.tasks.calculator.dto.Role;
import com.tasks.calculator.dto.Task;
import com.tasks.calculator.services.OperationService;
import com.tasks.calculator.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/rest/api/tasks/operations")
public class OperationController {
    private final OperationService operationService;
    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/")
    public String listAll(Model model) {
        List<Operation> operationList = this.operationService.listAll();
        model.addAttribute("listOperations", operationList);
        return "operations/operationList";
    }

    @GetMapping("/new")
    public String showNewOperationPage(Model model) {
        Operation operation = new Operation();
        model.addAttribute("operation", operation);
        return "operation/new_operation";
    }

    @PostMapping(value = "/save")
    public String saveOperation(@ModelAttribute("task") Task task, @ModelAttribute("operation") Operation operation) {
        this.operationService.save(task, operation);
        return "redirect:/api/operations/operation";
    }
}
