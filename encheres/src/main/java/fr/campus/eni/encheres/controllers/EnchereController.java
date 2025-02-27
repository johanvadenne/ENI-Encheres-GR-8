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
    
    List<Enchere> liste = enchereService.getAll();
    
    model.addAttribute("listeEncheres", liste);
    
    return "pages/encheres/liste-encheres";
  }
}
