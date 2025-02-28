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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.campus.eni.encheres.bo.ArticleVendu;
import fr.campus.eni.encheres.bo.Retrait;

@Repository
public class ArticleVenduRepositoryImpl implements ICrudRepository<ArticleVendu> {

    private static final Logger logger = LoggerFactory.getLogger(ArticleVenduRepositoryImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final CategorieRepositoryImpl categorieRepository;
    private final RetraitRepositoryImpl retraitRepository;
    private final UtilisateurRepositoryImpl utilisateurRepository;

    public ArticleVenduRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                      JdbcTemplate jdbcTemplate,
                                      CategorieRepositoryImpl categorieRepository,
                                      RetraitRepositoryImpl retraitRepository,
                                      UtilisateurRepositoryImpl utilisateurRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.categorieRepository = categorieRepository;
        this.retraitRepository = retraitRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public void add(ArticleVendu article) {
        String sql = """
            INSERT INTO articles_vendus
            (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)
            VALUES (:nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :prixInitial, :prixVente, :noUtilisateur, :noCategorie)
            RETURNING no_article
        """;
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article), keyHolder, new String[]{"no_article"});
        
        Optional.ofNullable(keyHolder.getKey()).ifPresent(id -> article.setNoArticle(id.intValue()));
    }

    @Override
    public List<ArticleVendu> getAll() {
        String sql = "SELECT * FROM articles_vendus";
        List<ArticleVendu> articles = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArticleVendu.class));
        articles.forEach(this::hydrateArticle);
        return articles;
    }

    public List<ArticleVendu> findByDateFinEncheres() {
        String sql = "SELECT * FROM articles_vendus WHERE date_fin_encheres < NOW()";
        List<ArticleVendu> articles = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArticleVendu.class));
        articles.forEach(this::hydrateArticle);
        return articles;
    }

    @Override
    public Optional<ArticleVendu> getById(int id) {
        String sql = "SELECT * FROM articles_vendus WHERE no_article = ?";
        try {
            ArticleVendu article = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ArticleVendu.class), id);
            hydrateArticle(article);
            return Optional.ofNullable(article);
        } catch (DataAccessException e) {
            logger.warn("Article non trouvé: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void update(ArticleVendu article) {
        String sql = """
            UPDATE articles_vendus SET
            nom_article = :nomArticle,
            description = :description,
            date_debut_encheres = :dateDebutEncheres,
            date_fin_encheres = :dateFinEncheres,
            prix_initial = :prixInitial,
            prix_vente = :prixVente,
            no_utilisateur = :noUtilisateur,
            etatvente = :etatVente,
            no_categorie = :noCategorie
            WHERE no_article = :noArticle
        """;
        
        int rows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(article));
        if (rows != 1) {
            throw new RuntimeException("Échec de la mise à jour de l'article: " + article);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM articles_vendus WHERE no_article = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows != 1) {
            throw new RuntimeException("Échec de la suppression de l'article, ID: " + id);
        }
    }

    private void hydrateArticle(ArticleVendu article) {
        if (article == null) return;

        categorieRepository.getById(article.getNoCategorie())
            .ifPresent(article::setCategorie);

        retraitRepository.getById(article.getNoArticle())
            .ifPresentOrElse(article::setRetrait,
                () -> {
                    article.setRetrait(new Retrait("", "", ""));
                });

        utilisateurRepository.getById(article.getNoUtilisateur())
            .ifPresent(article::setVendeur);
    }
}
