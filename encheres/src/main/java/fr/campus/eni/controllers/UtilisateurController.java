package fr.campus.eni.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.campus.eni.bll.UtilisateurServiceImpl;
import fr.campus.eni.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private UtilisateurServiceImpl UtilisateurServiceImpl;

    UtilisateurController(UtilisateurServiceImpl UtilisateurServiceImpl) {
        this.UtilisateurServiceImpl = UtilisateurServiceImpl;
    }

    @GetMapping("/inscrire")
    public String formulaireIncriptionUtilisateur(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("body", "pages/utilisateurs/formulaire-utilisateur");
        return "index";
    }

    @GetMapping("/connexion")
    public String formulaireConnexionUtilisateur(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("body", "pages/utilisateurs/formulaire-utilisateur");
        return "index";
    }

    @PostMapping("/enregistrer")
    public String enregistrerNouvelUtilisateur(Model model,  @Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult resultatValidation) {
    	if(resultatValidation.hasErrors()) {
    		model.addAttribute("body", "pages/utilisateurs/formulaire-inscription");
            return "index"; 
    	}
        UtilisateurServiceImpl.save(utilisateur);
        return "redirect:/utilisateurs";
    }
}