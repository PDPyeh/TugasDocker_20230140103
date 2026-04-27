package com.praktikum6.deploy.controller;

import com.praktikum6.deploy.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    
    // In-memory storage untuk users
    private List<User> userList = new ArrayList<>();
    private boolean isLoggedIn = false;
    
    @GetMapping("/")
    public String index() {
        if (isLoggedIn) {
            return "redirect:/home";
        }
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // Validasi credential
        if ("admin".equals(username) && "20230140103".equals(password)) {
            isLoggedIn = true;
            return "redirect:/home";
        }
        model.addAttribute("error", "Username atau Password salah!");
        return "login";
    }
    
    @GetMapping("/home")
    public String home(Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        
        // Hitung jumlah berdasarkan gender
        long maleCount = userList.stream().filter(u -> "Laki-laki".equals(u.getJenisKelamin())).count();
        long femaleCount = userList.stream().filter(u -> "Perempuan".equals(u.getJenisKelamin())).count();
        
        model.addAttribute("title", "Tabel Deployment Praktikum 6");
        model.addAttribute("users", userList);
        model.addAttribute("totalUsers", userList.size());
        model.addAttribute("maleCount", maleCount);
        model.addAttribute("femaleCount", femaleCount);
        return "home";
    }
    
    @GetMapping("/form")
    public String formPage(Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("user", new User());
        return "form";
    }
    
    @PostMapping("/form")
    public String submitData(@ModelAttribute User user) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        userList.add(user);
        return "redirect:/home";
    }
    
    @GetMapping("/logout")
    public String logout() {
        isLoggedIn = false;
        return "redirect:/login";
    }
}
