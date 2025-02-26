package fr.campus.eni.encheres.bo;

import java.util.Date;

public class Enchere {

    private Integer noEnchere;
    private Date dateEnchere;
    private Integer montantEnchere;
    private Integer noArticle;
    private ArticleVendu article;
    private Integer noUtilisateur;
    private Utilisateur utilisateur;

    public Enchere() {
    }

    public Enchere(Integer noEnchere, Date dateEnchere, Integer montantEnchere, ArticleVendu article,
            Utilisateur utilisateur, Integer noArticle, Integer noUtilisateur) {
        this.noEnchere = noEnchere;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
        this.article = article;
        this.utilisateur = utilisateur;
        this.noArticle = noArticle;
        this.noUtilisateur = noUtilisateur;
    }

    public Integer getNoEnchere() {
        return noEnchere;
    }

    public void setNoEnchere(Integer noEnchere) {
        this.noEnchere = noEnchere;
    }

    public Date getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(Date dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public Integer getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(Integer montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public ArticleVendu getArticle() {
        return article;
    }

    public void setArticle(ArticleVendu article) {
        this.article = article;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Integer getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(Integer noArticle) {
        this.noArticle = noArticle;
    }

    public Integer getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(Integer noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }

}
