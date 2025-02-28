package fr.campus.eni.encheres.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fr.campus.eni.encheres.bll.ArticleVenduServiceImpl;
import fr.campus.eni.encheres.bll.CategorieServiceImpl;
import fr.campus.eni.encheres.bll.EnchereServiceImpl;
import fr.campus.eni.encheres.bll.RetraitServiceImpl;
import fr.campus.eni.encheres.bo.ArticleVendu;
import fr.campus.eni.encheres.bo.Categorie;
import fr.campus.eni.encheres.bo.Enchere;
import fr.campus.eni.encheres.bo.Retrait;
import fr.campus.eni.encheres.bo.Utilisateur;
import fr.campus.eni.encheres.dal.UtilisateurRepositoryImpl;

@Controller
public class ArticleVenduController {

  private final ArticleVenduServiceImpl articleService;
  private final CategorieServiceImpl categorieService;
  private final RetraitServiceImpl retraitServiceImpl;
  private final EnchereServiceImpl enchereServiceImpl;
  private final UtilisateurRepositoryImpl utilisateurRepositoryImpl;

  public ArticleVenduController(
      ArticleVenduServiceImpl articleService,
      CategorieServiceImpl categorieService,
      RetraitServiceImpl retraitServiceImpl,
      EnchereServiceImpl enchereServiceImpl,
      UtilisateurRepositoryImpl utilisateurRepositoryImpl) {
    this.articleService = articleService;
    this.categorieService = categorieService;
    this.retraitServiceImpl = retraitServiceImpl;
    this.enchereServiceImpl = enchereServiceImpl;
    this.utilisateurRepositoryImpl = utilisateurRepositoryImpl;
  }

  @GetMapping("/listeVentes")
  public String listerVentes(
      @RequestParam(required = false) String nomArticle,
      @RequestParam(required = false) Integer categorie,
      @RequestParam(required = false) String typeVente,
      @RequestParam(required = false, defaultValue = "false") Boolean enchereOuvert,
      @RequestParam(required = false, defaultValue = "false") Boolean mesEncheres,
      @RequestParam(required = false, defaultValue = "false") Boolean mesEncheresRemporter,
      @RequestParam(required = false, defaultValue = "false") Boolean EnCours,
      @RequestParam(required = false, defaultValue = "false") Boolean nonDebutees,
      @RequestParam(required = false, defaultValue = "false") Boolean terminee,
      Model model,
      Principal principal) {
    List<ArticleVendu> articles = articleService.getAll();
    List<ArticleVendu> lesArticles = new ArrayList<ArticleVendu>();
    Utilisateur utilisateur = utilisateurRepositoryImpl.getByPseudo(principal.getName()).get();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        lesArticles.add(article);
      }

      if (typeVente != null) {
        if (typeVente.equals("achat")) {
          // garde seulement les ventes selon les paramètre enchereOuvert, mesEncheres, mesEncheresRemporter
                Optional<Enchere> meilleureEnchereOpt = enchereServiceImpl.getLastEnchereByArticle(article.getNoArticle());
                if (meilleureEnchereOpt.isPresent()) {
                  Enchere meilleureEnchere = meilleureEnchereOpt.get();

                  lesArticles.removeIf(articleVendu -> (enchereOuvert && articleVendu.getDateDebutEncheres().after(new Date()))); 
                  lesArticles.removeIf(articleVendu -> 
                    (articleVendu.getNoUtilisateur() != utilisateur.getNoUtilisateur() && mesEncheres) 
                    || (mesEncheresRemporter && 
                          (articleVendu.getNoArticle() != meilleureEnchere.getNoArticle() 
                          || utilisateur.getNoUtilisateur() != meilleureEnchere.getNoUtilisateur()) 
                        && articleVendu.getEtatVente() == false));
                }
                else {
                  lesArticles.removeIf(articleVendu -> (articleVendu.getNoUtilisateur() == utilisateur.getNoUtilisateur() && mesEncheres) || (enchereOuvert && articleVendu.getDateDebutEncheres().after(new Date())) );
                }
        } else if (typeVente.equals("mesVentes")) {
          // garde seulement mes vente
          lesArticles.removeIf(articleVendu -> articleVendu.getNoUtilisateur() != utilisateur.getNoUtilisateur() || articleVendu.getEtatVente() == EnCours || articleVendu.getEtatVente() != terminee || (articleVendu.getDateDebutEncheres().after(new Date()) && nonDebutees));
          
        }
      }
    }

    Collections.sort(lesArticles, Comparator.comparing(ArticleVendu::getNoArticle).reversed()); 
    
    model.addAttribute("pseudo", utilisateur.getPseudo());
    model.addAttribute("lesArticlesVendus", lesArticles);
    model.addAttribute("categories", categorieService.getAll());
    model.addAttribute("selectedCategorie", categorie);
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
  public String soumettreArticle(
      @ModelAttribute("articleVendu") ArticleVendu articleVendu,
      @ModelAttribute("retrait") Retrait retrait,
      @RequestParam("image") MultipartFile image,
      Principal principal)
      throws IOException {

    String pseudo = principal.getName();
    Utilisateur utilisateur = utilisateurRepositoryImpl.getByPseudo(pseudo).get();
    articleVendu.setNoUtilisateur(utilisateur.getNoUtilisateur());
    articleService.save(articleVendu);

    String originalFileName = image.getOriginalFilename();
    String extension = StringUtils.getFilenameExtension(originalFileName);

    if (extension == null || extension.isEmpty()) {
      throw new IllegalArgumentException("Format de fichier invalide !");
    }

    String uploadDir = "D:/uploads"; // Dossier où stocker les images
    String fileName =
        articleVendu.getNoArticle().toString() + "." + extension;
    Path filePath = Paths.get(uploadDir, fileName);

    // Sauvegarder le fichier sur le disque
    Files.createDirectories(filePath.getParent());
    Files.write(filePath, image.getBytes());

    retrait.setNoArticle(articleVendu.getNoArticle());
    retraitServiceImpl.save(retrait);
    return "redirect:/listeVentes";
  }

  @GetMapping("/vente/{id}")
  public String afficherDetailsVente(@PathVariable("id") int id, Model model, Principal principal) {
    ArticleVendu article = articleService.getById(id).get();
    Utilisateur utilisateur = utilisateurRepositoryImpl.getByPseudo(principal.getName()).get();
    List<Enchere> liste = enchereServiceImpl.getByNoArticle(id);
    Collections.sort(liste, Comparator.comparing(Enchere::getMontantEnchere).reversed());   

    model.addAttribute("listeEncheres", liste);
    model.addAttribute("article", article);
    model.addAttribute("pseudo", utilisateur.getPseudo());
    return "pages/ventes/detailVente";
  }

  // 💰 Traitement de l'enchère
  @PostMapping("/vente/{noArticle}/encherir")
  public String creerEnchere(
      @PathVariable Integer noArticle, @RequestParam Integer montantEnchere, Principal principal) {
    ArticleVendu article = articleService.getById(noArticle).get();
    Utilisateur utilisateur = utilisateurRepositoryImpl.getByPseudo(principal.getName()).get();
    List<Enchere> liste = enchereServiceImpl.getByNoArticle(noArticle);
    List<Enchere> listeEnchereUtilisateur = enchereServiceImpl.getEnhereValidByNoUtilisateur(utilisateur.getNoUtilisateur());

    if (liste.size() > 0) {
        Enchere derniereEnchere = liste.get(0);

        for (Enchere enchere : liste) {
          if (enchere.getMontantEnchere() > derniereEnchere.getMontantEnchere()) {
            derniereEnchere = enchere;
          }
        }

      if (montantEnchere <= derniereEnchere.getMontantEnchere()) {
        return "redirect:/vente/" + noArticle; // 🔄 Redirection vers la page de détails
      }
    }

    if (listeEnchereUtilisateur.size() > 0) {

        for (Enchere enchere : listeEnchereUtilisateur) {
          if (enchere.getNoUtilisateur() == utilisateur.getNoUtilisateur() && enchere.getNoArticle() == noArticle) {
            return "redirect:/vente/" + noArticle;
          }
        }
        
      int sommeEnchereUtilisateur = liste.stream().mapToInt(Enchere::getMontantEnchere).sum(); 

      if (montantEnchere > utilisateur.getCredit() - sommeEnchereUtilisateur) {
        return "redirect:/vente/" + noArticle;
      }
    }

    // si le vendeur de l'acticle est le m^me que celui qui enchère alors on ne peut pas enchérir
    if (article.getVendeur().getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
      return "redirect:/vente/" + noArticle;
    }

    Enchere enchere = new Enchere();
    enchere.setArticle(article);
    enchere.setNoArticle(noArticle);
    enchere.setNoUtilisateur(utilisateur.getNoUtilisateur());
    enchere.setUtilisateur(utilisateur);
    enchere.setMontantEnchere(montantEnchere);
    enchere.setDateEnchere(new Date());

    enchereServiceImpl.save(enchere);

    return "redirect:/vente/" + noArticle; // 🔄 Redirection vers la page de détails
  }

  @GetMapping("/vente/{noArticle}/modifier")
  public String afficherFormulaireModification(@PathVariable Integer noArticle, Model model) {
    ArticleVendu article = articleService.getById(noArticle).get();
    List<Categorie> categories = categorieService.getAll();
    model.addAttribute("articleVendu", article);
    model.addAttribute("categories", categories);
    return "pages/ventes/modifierVente";
  }

  @PostMapping("/vente/{noArticle}/modifier")
  public String soumettreModification(
      @PathVariable Integer noArticle,
      @ModelAttribute("articleVendu") ArticleVendu articleVendu,
      @ModelAttribute("retrait") Retrait retrait,
      @RequestParam("image") MultipartFile image)
      throws IOException {
    articleVendu.setNoArticle(noArticle);
    articleService.save(articleVendu);

    String originalFileName = image.getOriginalFilename();
    String extension = StringUtils.getFilenameExtension(originalFileName);

    if (extension == null || extension.isEmpty()) {
      throw new IllegalArgumentException("Format de fichier invalide !");
    }

    String uploadDir = "D:/uploads"; // Dossier où stocker les images
    String fileName =
        articleVendu.getNoArticle().toString()
            + "-"
            + articleVendu.getVendeur().getPseudo()
            + "."
            + extension;
    Path filePath = Paths.get(uploadDir, fileName);

    // Sauvegarder le fichier sur le disque
    Files.createDirectories(filePath.getParent());
    Files.write(filePath, image.getBytes());

    retrait.setNoArticle(articleVendu.getNoArticle());
    retraitServiceImpl.save(retrait);
    return "redirect:/vente/" + noArticle;
  }
}
