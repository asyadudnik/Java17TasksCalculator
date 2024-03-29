package com.tasks.calculator.rest;

import com.tasks.calculator.dto.Operation;
import com.tasks.calculator.dto.Task;
import com.tasks.calculator.repositories.OperationRepository;
import com.tasks.calculator.services.OperationService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.tasks.calculator.rest.global.HomeController.*;
import static com.tasks.calculator.utils.JsonUtils.toJson;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Controller
@RequestMapping("/rest/api/tasks/operations")
public class OperationController {
    private final OperationRepository operationRepository;
    private final OperationService operationService;

    @Autowired
    public OperationController(OperationRepository operationRepository) {
        this.operationService = new OperationService(operationRepository);
        this.operationRepository = operationRepository;
    }

    @GetMapping(value = "/", produces = {APPLICATION_JSON_VALUE})
    public String listAll(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Operation> operations = this.operationRepository.findAll(PageRequest.of(page, 10));
        model.addAttribute("operations", operations);
        model.addAttribute("currentPage", page);
        model.addAttribute(VIEW_NAME, "/operations/operationsList");

        try {
            operations.forEach(operation ->
                    log.info(toJson(operation))
            );
            return (String) model.getAttribute(VIEW_NAME);
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            model.addAttribute(ERR_MSG, exMessage);
            model.addAttribute(VIEW_NAME, ERR_PAGE);
            log.error(exMessage);
            return REDIRECT + ERR_PAGE;
        }
    }

    @GetMapping(value = "/new", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String showNewOperationPage(Model model) {
        Operation operation = new Operation();
        String taskName = (String) model.getAttribute("task");
        if(StringUtils.isBlank(taskName)){
            operation.setTaskName(taskName);
        }
        model.addAttribute("operation", operation);
        return "/operations/new_operation";
    }

    @PostMapping(value = "/save", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
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
