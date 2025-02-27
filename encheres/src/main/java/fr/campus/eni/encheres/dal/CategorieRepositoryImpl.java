package fr.campus.eni.encheres.dal;

import fr.campus.eni.encheres.bo.Categorie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategorieRepositoryImpl implements ICrudRepository<Categorie> {

  Logger logger = LoggerFactory.getLogger(CategorieRepositoryImpl.class);

  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private JdbcTemplate jdbcTemplate;

  public CategorieRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public void add(Categorie unCategorie) {
    String sql =
        """
            INSERT INTO categories
                (libelle)
             VALUES
                (:libelle)
        """;
    namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(unCategorie));
  }

  @Override
  public List<Categorie> getAll() {
    String sql =
        """
            select no_categorie, libelle
        from categories
        """;
    List<Categorie> categories =
        namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Categorie.class));

    return categories;
  }

  @Override
  public Optional<Categorie> getById(int id) {
    String sql =
        """
            select
              no_categorie, libelle
        from categories where no_categorie = ?
        """;
    Categorie categorie = null;
    try {
      categorie =
          jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Categorie.class), id);
    } catch (DataAccessException exc) {
      exc.printStackTrace();
      logger.warn(exc.getMessage());
    }

    return Optional.ofNullable(categorie);
  }

  @Override
  public void update(Categorie categorie) {
    String sql = "update categories set libelle=:libelle where no_categorie = :no_categorie";
    Map<String, Object> params = new HashMap<>();
    params.put("libelle", categorie.getLibelle());
    params.put(
        "no_categorie", categorie.getNoCategorie()); // Utilisez le nom de méthode getter adapté

    int nbRows = namedParameterJdbcTemplate.update(sql, params);
    if (nbRows != 1) {
      throw new RuntimeException("La modification de la catégorie a échoué : " + categorie);
    }
  }

  @Override
  public void delete(int id) {
    String sql = "delete from categories where no_categorie = ? ";
    int nbRows = jdbcTemplate.update(sql, id);
    if (nbRows != 1) {
      throw new RuntimeException("La suppression de l'categorie a échouée : id= " + id);
    }
  }
}
