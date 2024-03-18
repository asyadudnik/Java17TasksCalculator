package com.tasks.calculator.controllers;

import com.tasks.calculator.entities.Role;
import com.tasks.calculator.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/api/api/tasks/roles")
public class RoleController {
    private final RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String listAll(Model model) {
        List<Role> roleList = this.roleService.listAll();
        model.addAttribute("listRoles", roleList);
        return "role/roles";
    }

    @GetMapping("/new")
    public String showNewRolePage(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return "role/new_role";
    }

    @PostMapping(value = "/save")
    public String saveRole(@ModelAttribute("role") Role role) {
        this.roleService.save(role);
        return "redirect:/api/roles/role";
    }
}
