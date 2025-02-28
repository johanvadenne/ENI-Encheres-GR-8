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

import fr.campus.eni.encheres.bo.Retrait;

@Repository
public class RetraitRepositoryImpl implements ICrudRepository<Retrait> {

    private static final Logger logger = LoggerFactory.getLogger(RetraitRepositoryImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public RetraitRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
    }

    @Override
    public void add(Retrait retrait) {
        String sql = """
            INSERT INTO retraits (no_article, rue, code_postal, ville)
            VALUES (:noArticle, :rue, :codePostal, :ville)
        """;
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(retrait));
    }

    @Override
    public List<Retrait> getAll() {
        String sql = "SELECT no_article, rue, code_postal, ville FROM retraits";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Retrait.class));
    }

    @Override
    public Optional<Retrait> getById(int id) {
        String sql = "SELECT no_article, rue, code_postal, ville FROM retraits WHERE no_article = ?";
        try {
            Retrait retrait = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Retrait.class), id);
            return Optional.ofNullable(retrait);
        } catch (DataAccessException e) {
            logger.warn("Retrait non trouvé pour l'article: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public void update(Retrait retrait) {
        String sql = """
            UPDATE retraits 
            SET rue = :rue, code_postal = :codePostal, ville = :ville 
            WHERE no_article = :noArticle
        """;
        int rows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(retrait));
        if (rows != 1) {
            throw new RuntimeException("Échec de la mise à jour du retrait: " + retrait);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM retraits WHERE no_article = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows != 1) {
            throw new RuntimeException("Échec de la suppression du retrait, ID: " + id);
        }
    }
}
