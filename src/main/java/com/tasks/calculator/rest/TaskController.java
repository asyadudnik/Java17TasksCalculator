package com.tasks.calculator.rest;

import com.tasks.calculator.dto.Task;
import com.tasks.calculator.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/rest/api/tasks")
public class TaskController {
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String listAll(Model model) {
        List<Task> taskList = this.taskService.findAll();
        model.addAttribute("listTasks", taskList);
        return "tasks/tasksList";
    }

    @GetMapping("/new")
    public String showNewTaskPage(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "tasks/new_task";
    }

    @PostMapping(value = "/save")
    public String saveTask(@ModelAttribute("task") Task task) {
        this.taskService.save(task);
        return "redirect:/rest/api/tasks/tasksList";
    }
}
