package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    TaskRepository taskRepository;

    @RequestMapping("/")
    public String listTaks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String loadTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "taskform";
    }

    @PostMapping("/process")
    public String processTaskForm(@Valid Task task, BindingResult result) {
        if (result.hasErrors()) {
            return "taskform";
        }
        taskRepository.save(task);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", taskRepository.findById(id).get());
        return "showtasks";
    }

    @RequestMapping("/update/{id}")
    public String updateTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", taskRepository.findById(id).get());
        return "taskform";
    }

    @RequestMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }
}

