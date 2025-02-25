package fr.campus.eni.encheres.dal;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.campus.eni.encheres.bo.ArticleVendu;

@Repository
public class ArticleVenduRepositoryImpl implements ICrudRepository<ArticleVendu>{
    Logger logger = LoggerFactory.getLogger(ArticleVenduRepositoryImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public ArticleVenduRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }



    @Override
    public void add(ArticleVendu unArticleVendu) {
        String sql = """
            INSERT INTO articleVendus
                (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
             VALUES
                (:no_article, :nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)
        """;
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(unArticleVendu));
    }


    @Override
    public List<ArticleVendu> getAll() {
        String sql = """
        select 
          no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie
				from 
          articleVendus""";
		List<ArticleVendu> articleVendus = namedParameterJdbcTemplate.query(sql,
				new BeanPropertyRowMapper<>(ArticleVendu.class));

		return articleVendus;
    }

    @Override
    public Optional<ArticleVendu> getById(int id) {
        String sql = """
        select 
          no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie
        from 
          articleVendus where no_article = ?
        """;
        ArticleVendu articleVendu = null;
		try {
            articleVendu = jdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper<>(ArticleVendu.class), id);
		}catch(DataAccessException exc) {
			exc.printStackTrace();
			logger.warn(exc.getMessage());
		}

		return Optional.ofNullable(articleVendu);
    }

    @Override
    public void update(ArticleVendu articleVendu) {
        String sql = """
        update 
          articleVendus 
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
		if(nbRows != 1) {
			throw new RuntimeException("La modification du client a échouée : " + articleVendu );
		}
    }

    @Override
    public void delete(int id) {
        String sql = "delete from articleVendus where no_article = ? ";
		int nbRows = jdbcTemplate.update(sql, id);
		if(nbRows != 1) {
			throw new RuntimeException("La suppression de l'articleVendu a échouée : id= " +id );
		}
    }
}
