package com.tasks.calculator.rest;

import com.tasks.calculator.dto.Task;
import com.tasks.calculator.repositories.TaskRepository;
import com.tasks.calculator.services.TaskService;
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
@RequestMapping("/rest/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskService = new TaskService(taskRepository);
        this.taskRepository = taskRepository;
    }

    @GetMapping(value="/", produces = {APPLICATION_JSON_VALUE})
    public String listAll(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Task> tasks = this.taskRepository.findAll(PageRequest.of(page, 10));
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentPage", page);
        model.addAttribute(VIEW_NAME, "/tasks/tasksList");


        try {
            tasks.forEach(usr ->
                    log.info(toJson(usr))
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


    @GetMapping(value="/new", produces = {APPLICATION_JSON_VALUE})
    public String showNewTaskPage(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "/tasks/new_task";
    }

    @PostMapping(value = "/save", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String saveTask(@ModelAttribute("task") Task task) {
        this.taskService.save(task);
        return "/tasks/edit_task";
    }
    @DeleteMapping(value = "/delete/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String deleteTask(@PathVariable(name = "id") Long id) {
        this.taskService.delete(id);
        return "redirect:/tasks/tasksList";
    }

}
