package fr.campus.eni.encheres.controllers;


import fr.campus.eni.encheres.bll.UtilisateurServiceImpl;
import fr.campus.eni.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
  public String afficherFormulaireRegister(Model model) {
    model.addAttribute("utilisateur", new Utilisateur());
    return "pages/utilisateurs/formulaire-inscription";
  }

  @PostMapping("/register")
  public String enregistrerNouvelUtilisateur(
      Model model,
      @Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
      BindingResult resultatValidation) {
    if (resultatValidation.hasErrors()) {
      return "pages/utilisateurs/formulaire-inscription";
    }
    // Sauvegarde
    UtilisateurServiceImpl.save(utilisateur);

    // Redirection login
    return "redirect:/login";
  }

  @GetMapping("/logout")
  public String deconnexion(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
  }
}
