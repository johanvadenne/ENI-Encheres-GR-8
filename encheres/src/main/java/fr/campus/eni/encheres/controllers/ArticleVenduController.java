package fr.campus.eni.encheres.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.campus.eni.encheres.bll.ArticleVenduServiceImpl;
import fr.campus.eni.encheres.bo.ArticleVendu;

@Controller
public class ArticleVenduController {

    @Autowired
    private ArticleVenduServiceImpl articleVenduServiceImpl;

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
}
