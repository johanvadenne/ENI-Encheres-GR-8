package fr.campus.eni.encheres.bo;

import java.util.Date;

public class Enchere {
    
    private Integer noEnchere;
    private Date dateEnchere;
    private Integer montantEnchere;
    private ArticleVendu article;
    private Utilisateur utilisateur;

    public Enchere(Integer noEnchere, Date dateEnchere, Integer montantEnchere, ArticleVendu article,
            Utilisateur utilisateur) {
        this.noEnchere = noEnchere;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
        this.article = article;
        this.utilisateur = utilisateur;
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


    
}
