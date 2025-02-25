package fr.campus.eni.encheres.bo;


import java.time.LocalDateTime;

public class ArticleVendu {
    private Integer noArticle;
    private String nomArticle;
    private String description;
    private Categorie categorie;
    private Integer miseAPrix;
    private LocalDateTime dateDebutEncheres;
    private LocalDateTime dateFinEncheres;
    private Utilisateur vendeur;
    private Retrait retrait;
    
    public ArticleVendu(Integer noArticle, String nomArticle, String description, Categorie categorie,
            Integer miseAPrix, LocalDateTime dateDebutEncheres, LocalDateTime dateFinEncheres, Utilisateur vendeur,
            Retrait retrait) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.categorie = categorie;
        this.miseAPrix = miseAPrix;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.vendeur = vendeur;
        this.retrait = retrait;
    }

    public Integer getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(Integer noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Integer getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(Integer miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public LocalDateTime getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDateTime getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public Utilisateur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Utilisateur vendeur) {
        this.vendeur = vendeur;
    }

    public Retrait getRetrait() {
        return retrait;
    }

    public void setRetrait(Retrait retrait) {
        this.retrait = retrait;
    }

    
}

