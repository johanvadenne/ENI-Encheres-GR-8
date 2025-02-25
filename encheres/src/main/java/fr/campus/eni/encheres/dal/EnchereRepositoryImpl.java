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

import fr.campus.eni.encheres.bo.Enchere;

@Repository
public class EnchereRepositoryImpl implements ICrudRepository<Enchere> {

    Logger logger = LoggerFactory.getLogger(EnchereRepositoryImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public EnchereRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void add(Enchere unEnchere) {
        String sql = """
            INSERT INTO encheres
                (no_enchere, date_enchere, montant_enchere, no_article, no_utilisateur)
             VALUES
                (:no_enchere, :date_enchere, :montant_enchere, :no_article, :no_utilisateur)
        """;
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(unEnchere));
    }

    @Override
    public List<Enchere> getAll() {
        String sql = "select no_enchere, date_enchere, montant_enchere, no_article, no_utilisateur"
                + " from encheres";
        List<Enchere> encheres = namedParameterJdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Enchere.class));

        return encheres;
    }

    @Override
    public Optional<Enchere> getById(int id) {
        String sql = "select no_enchere, date_enchere, montant_enchere, no_article, no_utilisateur"
                + " from encheres where no_enchere = ?";
        Enchere enchere = null;
        try {
            enchere = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(Enchere.class), id);
        } catch (DataAccessException exc) {
            exc.printStackTrace();
            logger.warn(exc.getMessage());
        }

        return Optional.ofNullable(enchere);
    }

    @Override
    public void update(Enchere enchere) {
        String sql = "update encheres set date_enchere=:date_enchere, montant_enchere=:montant_enchere, no_article=:no_article, no_utilisateur=:no_utilisateur "
                + " where no_enchere = :no_enchere";
        int nbRows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(enchere));
        if (nbRows != 1) {
            throw new RuntimeException("La modification du client a échouée : " + enchere);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from encheres where no_enchere = ? ";
        int nbRows = jdbcTemplate.update(sql, id);
        if (nbRows != 1) {
            throw new RuntimeException("La suppression de l'enchere a échouée : id= " + id);
        }
    }
}
