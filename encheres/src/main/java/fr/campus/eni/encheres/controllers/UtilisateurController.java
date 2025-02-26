package fr.campus.eni.encheres.controllers;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.campus.eni.encheres.bo.Utilisateur;
import fr.campus.eni.encheres.dal.UtilisateurRepositoryImpl;
import jakarta.servlet.http.HttpSession;

@Controller
public class UtilisateurController {

    private final UtilisateurRepositoryImpl utilisateurRepositoryImpl;

    @Autowired
    public UtilisateurController(UtilisateurRepositoryImpl utilisateurRepositoryImpl) {
        this.utilisateurRepositoryImpl = utilisateurRepositoryImpl;
    }
    
    @GetMapping("/profil")
    public String afficherProfil(Model model, Principal principal) {
        String pseudo = principal.getName(); // Récupère le pseudo de l'utilisateur connecté
        Optional<Utilisateur> utilisateurOpt = utilisateurRepositoryImpl.getByPseudo(pseudo);

        if (utilisateurOpt.isPresent()) {
            model.addAttribute("utilisateur", utilisateurOpt.get());
        } else {
            model.addAttribute("erreur", "Utilisateur non trouvé.");
        }

        return "/pages/profils/profilDetail";
    }

}
