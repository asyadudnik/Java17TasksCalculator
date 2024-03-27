package com.tasks.calculator.rest;

import com.tasks.calculator.dto.Task;
import com.tasks.calculator.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Controller
@RequestMapping("/rest/api/tasks")
public class TaskController {
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value="/", produces = {APPLICATION_JSON_VALUE})
    public String listAll(Model model) {
        List<Task> taskList = this.taskService.findAll();
        model.addAttribute("listTasks", taskList);
        return "/tasks/tasksList";
    }

    @GetMapping(value="/new", produces = {APPLICATION_JSON_VALUE})
    public String showNewTaskPage(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "/tasks/new_task";
    }

    @PostMapping(value = "/save", produces = {APPLICATION_JSON_VALUE})
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
