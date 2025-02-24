package fr.campus.eni.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home() {
        // Cette méthode retourne le nom du template Thymeleaf
        return "index";
    }
}
