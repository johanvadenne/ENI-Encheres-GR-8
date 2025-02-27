package fr.campus.eni.encheres.bo;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class ArticleVendu {
  private Integer noArticle;
  private String nomArticle;
  private String description;
  private Integer no_categorie;
  private Categorie categorie;
  private Integer prixInitial;
  private Integer prixVente;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dateDebutEncheres;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dateFinEncheres;

  private Integer noUtilisateur;
  private Utilisateur vendeur;
  private Retrait retrait;

  public ArticleVendu() {
    // constructeur par défaut
  }

  public ArticleVendu(
      Integer noArticle,
      String nomArticle,
      String description,
      Categorie categorie,
      Integer prixInitial,
      Date dateDebutEncheres,
      Date dateFinEncheres,
      Utilisateur vendeur,
      Retrait retrait) {
    this.noArticle = noArticle;
    this.nomArticle = nomArticle;
    this.description = description;
    this.categorie = categorie;
    this.prixInitial = prixInitial;
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

  public Integer getPrixInitial() {
    return prixInitial;
  }

  public void setPrixInitial(Integer prixInitial) {
    this.prixInitial = prixInitial;
  }

  public Date getDateDebutEncheres() {
    return dateDebutEncheres;
  }

  public void setDateDebutEncheres(Date dateDebutEncheres) {
    this.dateDebutEncheres = dateDebutEncheres;
  }

  public Date getDateFinEncheres() {
    return dateFinEncheres;
  }

  public void setDateFinEncheres(Date dateFinEncheres) {
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

  @Override
  public String toString() {
    return "ArticleVendu [noArticle="
        + noArticle
        + ", nomArticle="
        + nomArticle
        + ", description="
        + description
        + ", categorie="
        + categorie
        + ", miseAPrix="
        + prixInitial
        + ", dateDebutEncheres="
        + dateDebutEncheres
        + ", dateFinEncheres="
        + dateFinEncheres
        + ", vendeur="
        + vendeur
        + ", retrait="
        + retrait
        + "]";
  }

  public Integer getNo_categorie() {
    return no_categorie;
  }

  public void setNo_categorie(Integer no_categorie) {
    this.no_categorie = no_categorie;
  }

  public Integer getPrixVente() {
    return prixVente;
  }

  public void setPrixVente(Integer prixVente) {
    this.prixVente = prixVente;
  }

  public Integer getNoUtilisateur() {
    return noUtilisateur;
  }

  public void setNoUtilisateur(Integer noUtilisateur) {
    this.noUtilisateur = noUtilisateur;
  }
}
