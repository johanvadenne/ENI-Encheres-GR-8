
package fr.campus.eni.bo;

public class Categorie {
    
    private Integer noCategorie;
    private String libelle;

    public Categorie(Integer noCategorie, String libelle) {
        this.noCategorie = noCategorie;
        this.libelle = libelle;
    }

    public Integer getNoCategorie() {
        return noCategorie;
    }
    public void setNoCategorie(Integer noCategorie) {
        this.noCategorie = noCategorie;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}