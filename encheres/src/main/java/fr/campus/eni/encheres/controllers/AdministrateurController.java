package fr.campus.eni.encheres.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.campus.eni.encheres.bll.CategorieServiceImpl;
import fr.campus.eni.encheres.bll.UtilisateurServiceImpl;
import fr.campus.eni.encheres.bo.Categorie;
import fr.campus.eni.encheres.bo.Utilisateur;

@RequestMapping("/admin")
@Controller
public class AdministrateurController {
  private CategorieServiceImpl categorieServiceImpl;
  private UtilisateurServiceImpl utilisateurServiceImpl;

  public AdministrateurController(CategorieServiceImpl categorieServiceImpl, UtilisateurServiceImpl utilisateurServiceImpl) {
    this.categorieServiceImpl = categorieServiceImpl;
    this.utilisateurServiceImpl = utilisateurServiceImpl;
  }

  @GetMapping("/gestionCategorie")
  public String gestionCategorie(Model model) {
    List<Categorie> categories = categorieServiceImpl.getAll();
    model.addAttribute("categories", categories);
    return "pages/admin/gestionCategorie";
  }

  @GetMapping("/ajouterCategorie")
  public String ajouterCategorie(Model model) {
    model.addAttribute("categorie", new Categorie());
    return "pages/admin/ajouterCategorie";
  }

  @PostMapping("/ajouterCategorie")
  public String soumettreCategorie(@ModelAttribute("categorie") Categorie categorie, Model model) {
    categorieServiceImpl.add(categorie);
    return "redirect:/admin/gestionCategorie";
  }

  @GetMapping("/supprimerCategorie/{id}")
  public String supprimerCategorie(@PathVariable Integer id) {
    categorieServiceImpl.delete(id);
    return "redirect:/admin/gestionCategorie";
  }

  @GetMapping("/modifierCategorie/{id}")
  public String modifierCategorie(@PathVariable Integer id, Model model) {
    Categorie categorie =
        categorieServiceImpl.getById(id).get(); // Méthode à ajouter dans votre service
    model.addAttribute("categorie", categorie);
    return "pages/admin/modifierCategorie"; // Une page pour modifier la catégorie
  }

  @PostMapping("/modifierCategorie/{id}")
  public String soumettreModificationCategorie(
      @PathVariable Integer id, @ModelAttribute("categorie") Categorie categorie, Model model) {
    categorie.setNoCategorie(id); // Assurer que l'ID ne change pas
    categorieServiceImpl.update(categorie);
    return "redirect:/admin/gestionCategorie";
  }

  @GetMapping("/gestionUtilisateur")
  public String gestionUtilisateur(Model model) {
    List<Utilisateur> lesUtilisateurs = utilisateurServiceImpl.getAll();
    model.addAttribute("lesUtilisateurs", lesUtilisateurs);
    return "pages/admin/gestionUtilisateur";
  }

  @GetMapping("/supprimer/{id}")
  public String supprimer(@PathVariable Integer id) {
    utilisateurServiceImpl.delete(id);
    return "redirect:/admin/gestionUtilisateur";
  }

  @GetMapping("/desactiver/{id}")
  public String desactiver(@PathVariable Integer id) {
    utilisateurServiceImpl.desactiverUtilisateur(id,true);
    return "redirect:/admin/gestionUtilisateur";
  }
}
