package fr.campus.eni.encheres.bo;

public class Utilisateur {
  private Integer noUtilisateur;
  private String pseudo;
  private String nom;
  private String prenom;
  private String email;
  private String telephone;
  private String rue;
  private String codePostal;
  private String ville;
  private String motDePasse;
  private Integer credit;
  private Boolean administrateur;
  private Boolean desactive;

  public Utilisateur() {}

  public Utilisateur(
      Integer noUtilisateur,
      String pseudo,
      String nom,
      String prenom,
      String email,
      String telephone,
      String rue,
      String codePostal,
      String ville,
      String motDePasse,
      Integer credit,
      Boolean administrateur,
      Boolean desactive) {
    this.noUtilisateur = noUtilisateur;
    this.pseudo = pseudo;
    this.nom = nom;
    this.prenom = prenom;
    this.email = email;
    this.telephone = telephone;
    this.rue = rue;
    this.codePostal = codePostal;
    this.ville = ville;
    this.motDePasse = motDePasse;
    this.credit = credit;
    this.administrateur = administrateur;
    this.desactive = desactive;
  }

  public Boolean getDesactive() {
    return desactive;
  }

  public void setDesactive(Boolean desactive) {
    this.desactive = desactive;
  }

  public Integer getNoUtilisateur() {
    return noUtilisateur;
  }

  public void setNoUtilisateur(Integer noUtilisateur) {
    this.noUtilisateur = noUtilisateur;
  }

  public String getPseudo() {
    return pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getRue() {
    return rue;
  }

  public void setRue(String rue) {
    this.rue = rue;
  }

  public String getCodePostal() {
    return codePostal;
  }

  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
  }

  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  public Integer getCredit() {
    return credit;
  }

  public void setCredit(Integer credit) {
    this.credit = credit;
  }

  public Boolean getAdministrateur() {
    return administrateur;
  }

  public void setAdministrateur(Boolean administrateur) {
    this.administrateur = administrateur;
  }
}
