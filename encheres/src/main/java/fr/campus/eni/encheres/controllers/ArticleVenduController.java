package fr.campus.eni.encheres.controllers;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.campus.eni.encheres.bll.ArticleVenduServiceImpl;
import fr.campus.eni.encheres.bll.CategorieServiceImpl;
import fr.campus.eni.encheres.bll.UtilisateurServiceImpl;
import fr.campus.eni.encheres.bo.ArticleVendu;
import fr.campus.eni.encheres.bo.Categorie;
import fr.campus.eni.encheres.bo.Retrait;
import fr.campus.eni.encheres.bo.Utilisateur;

@Controller
public class ArticleVenduController {

    @Autowired
    private ArticleVenduServiceImpl articleVenduServiceImpl;

    private final ArticleVenduServiceImpl articleService;
    private final CategorieServiceImpl categorieService;
    private final UtilisateurServiceImpl utilisateurService;

    public ArticleVenduController(ArticleVenduServiceImpl articleService, CategorieServiceImpl categorieService, UtilisateurServiceImpl utilisateurService) {
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/listeVentes")
    public String listerVentes(Model model) {
        List<ArticleVendu> articles = articleVenduServiceImpl.getAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (ArticleVendu article : articles) {
            article.setDateDebutEncheres(article.getDateDebutEncheres().formatted(formatter));
            article.setDateFinEncheres(article.getDateFinEncheres().formatted(formatter));
        }
        model.addAttribute("lesArticlesVendus", articles);
        return "pages/ventes/listeVente";  // Le nom de votre fichier HTML
    }



    @GetMapping("/creer-vente")
    public String afficherFormulaireVente(Model model, Principal principal) {
        List<Categorie> categories = categorieService.getAll();
        
        model.addAttribute("articleVendu", new ArticleVendu());
        model.addAttribute("categories", categories);
        

        return "pages/ventes/creerVente";
    }

    @PostMapping("/creer-vente")
    public String soumettreArticle(@ModelAttribute("articleVendu") ArticleVendu articleVendu) {
        System.out.println(articleVendu);
        articleService.save(articleVendu);
        return "redirect:/listeVentes";
    }
}
