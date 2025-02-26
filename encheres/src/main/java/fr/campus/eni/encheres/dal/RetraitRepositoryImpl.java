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
public class RetraitRepositoryImpl implements ICrudRepository<Retrait>{
    Logger logger = LoggerFactory.getLogger(RetraitRepositoryImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public RetraitRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }



    @Override
    public void add(Retrait unRetrait) {
        String sql = """
            INSERT INTO retraits
                (no_article, rue, code_postal, ville)
             VALUES
                (:no_article, :rue, :code_postal, :ville)
        """;
        namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(unRetrait));
    }


    @Override
    public List<Retrait> getAll() {
        String sql = "select no_article, rue, code_postal, ville"
				+ " from retraits";
		List<Retrait> retraits = namedParameterJdbcTemplate.query(sql,
				new BeanPropertyRowMapper<>(Retrait.class));

		return retraits;
    }

    @Override
    public Optional<Retrait> getById(int id) {
        String sql = "select no_article, rue, code_postal, ville"
				+ " from retraits where no_article = ?";
        Retrait retrait = null;
		try {
            retrait = jdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper<>(Retrait.class), id);
		}catch(DataAccessException exc) {
			exc.printStackTrace();
			logger.warn(exc.getMessage());
		}

		return Optional.ofNullable(retrait);
    }

    @Override
    public void update(Retrait retrait) {
        String sql = "update retraits set rue=:rue, code_postal=:code_postal, ville=:ville "
				+ " where no_article = :noArticle";
		int nbRows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(retrait));
		if(nbRows != 1) {
			throw new RuntimeException("La modification du client a échouée : " + retrait );
		}
    }

    @Override
    public void delete(int id) {
        String sql = "delete from retraits where no_retrait = ? ";
		int nbRows = jdbcTemplate.update(sql, id);
		if(nbRows != 1) {
			throw new RuntimeException("La suppression de l'retrait a échouée : id= " +id );
		}
    }

}
