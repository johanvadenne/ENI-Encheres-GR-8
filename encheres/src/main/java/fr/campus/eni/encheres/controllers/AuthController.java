package fr.campus.eni.encheres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String afficherFormulaireLogin() {
        // Retourne la vue Thymeleaf du formulaire de connexion
        return "pages/utilisateurs/formulaire-connexion";
    }

    @GetMapping("/register")
    public String afficherFormulaireRegister() {
        // Retourne la vue Thymeleaf du formulaire de connexion
        return "pages/utilisateurs/formulaire-inscription";
    }
}
