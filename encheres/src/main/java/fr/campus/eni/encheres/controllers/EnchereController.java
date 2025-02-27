package fr.campus.eni.encheres.controllers;

import fr.campus.eni.encheres.bll.EnchereServiceImpl;
import fr.campus.eni.encheres.bo.Enchere;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/encheres")
public class EnchereController {

  private final EnchereServiceImpl enchereService;

  public EnchereController(EnchereServiceImpl enchereService) {
    this.enchereService = enchereService;
  }

  @GetMapping
  public String listeEncheres(Model model) {
    // 1) Récupérer la liste des enchères
    List<Enchere> liste = enchereService.getAll();

    // 2) Ajouter cette liste au modèle
    model.addAttribute("listeEncheres", liste);

    // 3) Retourner le nom de la vue Thymeleaf
    // (adapte le chemin si besoin, ex: "pages/encheres/liste-encheres")
    return "pages/encheres/liste-encheres";
  }
}
