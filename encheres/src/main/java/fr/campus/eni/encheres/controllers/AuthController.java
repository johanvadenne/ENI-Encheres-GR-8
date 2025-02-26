package fr.campus.eni.encheres.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.campus.eni.encheres.bll.UtilisateurServiceImpl;
import fr.campus.eni.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/login")
public String connecterUtilisateur(
        @RequestParam("pseudo") String pseudo,
        @RequestParam("motDePasse") String motDePasse,
        Model model,
        HttpSession session // Injection de la session
) {
    try {
        Optional<Utilisateur> utilisateurOpt = UtilisateurServiceImpl.getByPseudoAndMdp(pseudo, motDePasse);
        
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();

            // 🗝️ Création de la session utilisateur
            session.setAttribute("utilisateurConnecte", utilisateur);

            return "redirect:/"; // Redirection après connexion réussie
        } else {
            model.addAttribute("erreur", "Identifiants invalides");
            return "pages/utilisateurs/formulaire-connexion";
        }

    } catch (Exception e) {
        model.addAttribute("erreur", "Une erreur est survenue. Veuillez réessayer.");
        return "pages/utilisateurs/formulaire-connexion";
    }
}

    @GetMapping("/register")
    public String afficherFormulaireRegister(Model model) {
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
