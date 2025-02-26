package fr.campus.eni.encheres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.campus.eni.encheres.bll.UtilisateurServiceImpl;
import fr.campus.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    private UtilisateurServiceImpl UtilisateurServiceImpl;

    AuthController(UtilisateurServiceImpl UtilisateurServiceImpl) {
        this.UtilisateurServiceImpl = UtilisateurServiceImpl;
    }

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

    @PostMapping("/register")
    public String enregistrerNouvelUtilisateur(
            Model model,
            @Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
            BindingResult resultatValidation
    ) {
        if (resultatValidation.hasErrors()) {
            return "pages/utilisateurs/formulaire-inscription";
        }
        // Sauvegarde
        UtilisateurServiceImpl.save(utilisateur);

        System.out.println("Utilisateur enregistré : " + utilisateur.getPrenom() + " " + utilisateur.getNom());

        // Redirection login
        return "redirect:/login";
    }
}
