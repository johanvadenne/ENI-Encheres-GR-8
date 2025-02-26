package fr.campus.eni.encheres.controllers;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.campus.eni.encheres.bll.ArticleVenduServiceImpl;
import fr.campus.eni.encheres.bll.CategorieServiceImpl;
import fr.campus.eni.encheres.bll.EnchereServiceImpl;
import fr.campus.eni.encheres.bll.RetraitServiceImpl;
import fr.campus.eni.encheres.bll.UtilisateurServiceImpl;
import fr.campus.eni.encheres.bo.ArticleVendu;
import fr.campus.eni.encheres.bo.Categorie;
import fr.campus.eni.encheres.bo.Enchere;
import fr.campus.eni.encheres.bo.Retrait;
import fr.campus.eni.encheres.bo.Utilisateur;

@Controller
public class ArticleVenduController {

    private final ArticleVenduServiceImpl articleService;
    private final CategorieServiceImpl categorieService;
    private final RetraitServiceImpl retraitServiceImpl;
    private final EnchereServiceImpl enchereServiceImpl;
    private UtilisateurServiceImpl utilisateurService;

    public ArticleVenduController(ArticleVenduServiceImpl articleService, CategorieServiceImpl categorieService,
            UtilisateurServiceImpl utilisateurService, RetraitServiceImpl retraitServiceImpl, EnchereServiceImpl enchereServiceImpl) {
        this.articleService = articleService;
        this.categorieService = categorieService;
        this.retraitServiceImpl = retraitServiceImpl;
        this.enchereServiceImpl = enchereServiceImpl;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/listeVentes")
    public String listerVentes(@RequestParam(required = false) String nomArticle,
            @RequestParam(required = false) Integer categorie,
            Model model) {
        List<ArticleVendu> articles = articleService.getAll();
        List<ArticleVendu> lesArticles = new ArrayList<ArticleVendu>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (nomArticle != null) {
            nomArticle = nomArticle.trim();
        }

        for (ArticleVendu article : articles) {
            boolean matchNom = true;
            boolean matchCategorie = true;

            if (nomArticle != null && !nomArticle.isEmpty()) {
                matchNom = article.getNomArticle().toLowerCase().contains(nomArticle.toLowerCase());
            }

            if (categorie != null) {
                matchCategorie = article.getCategorie().getNoCategorie() == categorie;
            }

            if (matchNom && matchCategorie) {
                // article.setDateDebutEncheres(article.getDateDebutEncheres().formatted(formatter));
                // article.setDateFinEncheres(article.getDateFinEncheres().formatted(formatter));
                lesArticles.add(article);
            }
        }

        model.addAttribute("lesArticlesVendus", lesArticles);
        model.addAttribute("categories", categorieService.getAll());
        return "pages/ventes/listeVente"; // Le nom de votre fichier HTML
    }

    @GetMapping("/creer-vente")
    public String afficherFormulaireVente(Model model, Principal principal) {
        List<Categorie> categories = categorieService.getAll();

        model.addAttribute("articleVendu", new ArticleVendu());
        model.addAttribute("categories", categories);

        return "pages/ventes/creerVente";
    }

    @PostMapping("/creer-vente")
    public String soumettreArticle(@ModelAttribute("articleVendu") ArticleVendu articleVendu, @ModelAttribute("retrait") Retrait retrait) {
        articleService.save(articleVendu);
        retrait.setNoCategorie(articleVendu.getNoArticle());
        retraitServiceImpl.save(retrait);
        return "redirect:/listeVentes";
    }

    
    @GetMapping("/vente/{id}")
    public String afficherDetailsVente(@PathVariable("id") int id, Model model) {
        ArticleVendu article = articleService.getById(id).get();
        model.addAttribute("article", article);
        return "pages/ventes/detailVente"; // Nom du template HTML (detailsVente.html)
    }

    
    // 💰 Traitement de l'enchère
    @PostMapping("/vente/{noArticle}/encherir")

    public String creerEnchere(@PathVariable Integer noArticle,
                               @RequestParam Integer montantEnchere,
                               Principal principal) {
        ArticleVendu article = articleService.getById(noArticle).get();
        Utilisateur utilisateur = utilisateurService.getById(17).get();

        Enchere enchere = new Enchere();
        enchere.setArticle(article);
        enchere.setNoArticle(noArticle);
        enchere.setNoUtilisateur(17);
        enchere.setUtilisateur(utilisateur);
        enchere.setMontantEnchere(montantEnchere);
        enchere.setDateEnchere(new Date());

        enchereServiceImpl.save(enchere);

        return "redirect:/vente/" + noArticle; // 🔄 Redirection vers la page de détails
    }
}
