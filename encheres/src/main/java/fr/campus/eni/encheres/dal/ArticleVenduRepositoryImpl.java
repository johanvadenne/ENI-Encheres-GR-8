package fr.campus.eni.encheres.dal;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.campus.eni.encheres.bo.ArticleVendu;
import fr.campus.eni.encheres.bo.Categorie;
import fr.campus.eni.encheres.bo.Retrait;
import fr.campus.eni.encheres.bo.Utilisateur;

@Repository
public class ArticleVenduRepositoryImpl implements ICrudRepository<ArticleVendu> {

    Logger logger = LoggerFactory.getLogger(ArticleVenduRepositoryImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CategorieRepositoryImpl categorieRepositoryImpl;
    @Autowired
    private RetraitRepositoryImpl retraitRepositoryImpl;
    @Autowired
    private UtilisateurRepositoryImpl utilisateurRepositoryImpl;

    public ArticleVenduRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void add(ArticleVendu unArticleVendu) {
        String sql = """
            INSERT INTO articles_vendus
                (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
             VALUES
                (:nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :prixInitial, :prixVente, 17, :no_categorie)
        """;
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(unArticleVendu));
    }

    @Override
    public List<ArticleVendu> getAll() {
        String sql = """
        select 
          no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie
				from 
          articles_vendus
          """;
        List<ArticleVendu> articleVendus = namedParameterJdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(ArticleVendu.class));
        for (ArticleVendu articleVendu : articleVendus) {
            String idCategorieSQL = "select no_categorie from articles_vendus where no_article = ?";
            int idCat = jdbcTemplate.queryForObject(idCategorieSQL, Integer.class, articleVendu.getNoArticle());
            Categorie categorie = categorieRepositoryImpl.getById(idCat).get();
            articleVendu.setCategorie(categorie);

            Optional<Retrait> retrait = retraitRepositoryImpl.getById(articleVendu.getNoArticle());
            if (retrait.isPresent()) {
                articleVendu.setRetrait(retrait.get());
            } else {
                articleVendu.setRetrait(new Retrait("", "", ""));
                logger.warn("Retrait non trouvé pour l'article " + articleVendu.getNoArticle());
            }

            String idUtilisateurSQL = "select no_utilisateur from articles_vendus where no_article = ?";
            int idUtil = jdbcTemplate.queryForObject(idUtilisateurSQL, Integer.class, articleVendu.getNoArticle());
            Utilisateur utilisateur = utilisateurRepositoryImpl.getById(idUtil).get();
            articleVendu.setVendeur(utilisateur);
        }

        return articleVendus;
    }

    @Override
    public Optional<ArticleVendu> getById(int id) {
        String sql = """
        select 
          no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie
        from 
          articles_vendus where no_article = ?
        """;
        ArticleVendu articleVendu = null;
        try {
            articleVendu = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(ArticleVendu.class), id);
        } catch (DataAccessException exc) {
            exc.printStackTrace();
            logger.warn(exc.getMessage());
        }

        return Optional.ofNullable(articleVendu);
    }

    @Override
    public void update(ArticleVendu articleVendu) {
        String sql = """
        update 
          articles_vendus 
            set 
	          nom_article=:nom_article,
	          description=:description,
	          date_debut_encheres=:date_debut_encheres,
	          date_fin_encheres=:date_fin_encheres,
	          prix_initial=:prix_initial,
	          prix_vente=:prix_vente,
	          no_utilisateur=:no_utilisateur,
	          no_categorie=:no_categorie
          where no_articleVendu = :noArticleVendu
        """;
        int nbRows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(articleVendu));
        if (nbRows != 1) {
            throw new RuntimeException("La modification du client a échouée : " + articleVendu);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from articles_vendus where no_article = ? ";
        int nbRows = jdbcTemplate.update(sql, id);
        if (nbRows != 1) {
            throw new RuntimeException("La suppression de l'articleVendu a échouée : id= " + id);
        }
    }
}
