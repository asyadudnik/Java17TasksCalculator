package com.tasks.calculator.rest.global;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(value={"/rest/api/tasks/"})
public class HomeController {
    @RequestMapping(value={"/home"})
    public String root(Locale locale, ModelMap model) {
        model.addAttribute("content", "content");
        Map<String, String> items = new HashMap<>();
        items.put("1.","Tasks list with status.");
        items.put("2.","Creation of task.");
        items.put("3.","Operation list with status.");
        items.put("4.","Creation of operation.");
        items.put("5.","Calculate tasks cost.");
        items.put("6.","Report.");
        model.addAttribute("items", items);
        return "home";
    }
}
