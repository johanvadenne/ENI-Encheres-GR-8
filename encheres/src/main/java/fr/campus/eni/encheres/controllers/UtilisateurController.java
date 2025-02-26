package fr.campus.eni.encheres.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.campus.eni.encheres.bll.UtilisateurServiceImpl;
import fr.campus.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private UtilisateurServiceImpl UtilisateurServiceImpl;

    UtilisateurController(UtilisateurServiceImpl UtilisateurServiceImpl) {
        this.UtilisateurServiceImpl = UtilisateurServiceImpl;
    }

    @GetMapping("/login")
        public String showLoginForm() {
        return "pages/utilisateurs/formulaire-connexion";  // Pas besoin de mettre .html
    }

    // @GetMapping("/login")
    // public String formulaireConnexionUtilisateur(Model model) {
    //     model.addAttribute("utilisateur", new Utilisateur());
    //     model.addAttribute("body", "pages/utilisateurs/formulaire-connexion");
    //     return "index";
    // }

    @GetMapping("/register")
    public String formulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
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

        // Redirection login
        return "redirect:/login";
    }
}