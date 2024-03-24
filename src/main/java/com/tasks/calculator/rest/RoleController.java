package com.tasks.calculator.rest;

import com.tasks.calculator.dto.Role;
import com.tasks.calculator.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/rest/api/roles")
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
        return "/roles/rolesList";
    }

    @GetMapping("/new")
    public String showNewRolePage(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return "/roles/new_role";
    }

    @PostMapping(value = "/save")
    public String saveRole(@ModelAttribute("role") Role role) {
        this.roleService.save(role);
        return "redirect:/rest/api/roles/rolesList";
    }
}
