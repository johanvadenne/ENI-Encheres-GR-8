package fr.campus.eni.encheres.controllers;

import fr.campus.eni.encheres.bo.Utilisateur;
import fr.campus.eni.encheres.dal.UtilisateurRepositoryImpl;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

  private final UtilisateurRepositoryImpl utilisateurRepositoryImpl;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UtilisateurController(
      UtilisateurRepositoryImpl utilisateurRepositoryImpl, PasswordEncoder passwordEncoder) {
    this.utilisateurRepositoryImpl = utilisateurRepositoryImpl;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/profil")
  public String afficherProfil(Model model, Principal principal) {
    String pseudo = principal.getName();
    Optional<Utilisateur> utilisateurOpt = utilisateurRepositoryImpl.getByPseudo(pseudo);

    if (utilisateurOpt.isPresent()) {
      model.addAttribute("utilisateur", utilisateurOpt.get());
    } else {
      model.addAttribute("erreur", "Utilisateur non trouvé.");
    }
    return "pages/profils/profilDetail";
  }

  @GetMapping("/profil/modifier")
  public String modifierProfil(Model model, Principal principal) {
    String pseudo = principal.getName();
    Optional<Utilisateur> utilisateurOpt = utilisateurRepositoryImpl.getByPseudo(pseudo);

    if (utilisateurOpt.isPresent()) {
      model.addAttribute("utilisateur", utilisateurOpt.get());
    } else {
      model.addAttribute("erreur", "Utilisateur non trouvé.");
    }
    return "pages/profils/profilModifier";
  }

  @PostMapping("/modifier")
  public String modifierProfilSubmit(
      @Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
      BindingResult bindingResult,
      Model model,
      Principal principal) {
    // Récupération de l'utilisateur courant depuis la BDD
    String pseudo = principal.getName();
    Optional<Utilisateur> utilisateurOpt = utilisateurRepositoryImpl.getByPseudo(pseudo);

    if (!utilisateurOpt.isPresent()) {
      model.addAttribute("erreur", "Utilisateur non trouvé.");
      return "redirect:/utilisateurs/profil";
    }

    Utilisateur userFromDb = utilisateurOpt.get();

    // Si le champ mot de passe est vide, conserver le mot de passe actuel,
    // sinon, encoder le nouveau mot de passe.
    if (utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().trim().isEmpty()) {
      utilisateur.setMotDePasse(userFromDb.getMotDePasse());
    } else {
      utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
    }

    // S'assurer de conserver l'identifiant de l'utilisateur courant
    utilisateur.setNoUtilisateur(userFromDb.getNoUtilisateur());

    // Si des erreurs de validation sont présentes, renvoyer vers le formulaire
    if (bindingResult.hasErrors()) {
      return "pages/profils/profilModifier";
    }

    // Mise à jour de l'utilisateur en base
    utilisateurRepositoryImpl.update(utilisateur);

    return "redirect:/utilisateurs/profil";
  }

  @PostMapping("/supprimer")
  public String supprimerProfil(Principal principal) {
    String pseudo = principal.getName();
    Optional<Utilisateur> utilisateurOpt = utilisateurRepositoryImpl.getByPseudo(pseudo);

    if (utilisateurOpt.isPresent()) {
      utilisateurRepositoryImpl.delete(utilisateurOpt.get().getNoUtilisateur());
    }

    return "redirect:/logout";
  }

}
