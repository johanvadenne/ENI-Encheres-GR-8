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

import fr.campus.eni.encheres.bo.Utilisateur;

@Repository
public class UtilisateurRepositoryImpl implements ICrudRepository<Utilisateur> {
  Logger logger = LoggerFactory.getLogger(UtilisateurRepositoryImpl.class);

  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private JdbcTemplate jdbcTemplate;

  public UtilisateurRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public void add(Utilisateur unUtilisateur) {
    String sql = """
            INSERT INTO utilisateurs
                (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit)
             VALUES
                (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse, :credit)
        """;
    namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(unUtilisateur));
  }

  @Override
  public List<Utilisateur> getAll() {
    String sql = "select no_utilisateur as noUtilisateur, pseudo, nom, prenom, email, telephone, rue,"
        + " code_postal as codePostal, ville, mot_de_passe as motDePasse, credit,"
        + " administrateur, desactive from utilisateurs";
    List<Utilisateur> utilisateurs = namedParameterJdbcTemplate.query(sql,
        new BeanPropertyRowMapper<>(Utilisateur.class));

    return utilisateurs;
  }

  @Override
  public Optional<Utilisateur> getById(int id) {
    String sql = "select no_utilisateur as noUtilisateur, pseudo, nom, prenom, email, telephone, rue,"
        + " code_postal as codePostal, ville, mot_de_passe as motDePasse, credit,"
        + " administrateur, desactive from utilisateurs where no_utilisateur = ?";
    Utilisateur utilisateur = null;
    try {
      utilisateur = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Utilisateur.class), id);
    } catch (DataAccessException exc) {
      exc.printStackTrace();
      logger.warn(exc.getMessage());
    }

    return Optional.ofNullable(utilisateur);
  }

  @Override
  public void update(Utilisateur utilisateur) {
    String sql = """
        update utilisateurs
        set
        pseudo=:pseudo,
        nom=:nom,
        prenom=:prenom,
        email=:email,
        telephone=:telephone,
        rue=:rue,
        code_postal=:codePostal,
        ville=:ville,
        mot_de_passe=:motDePasse
        where no_utilisateur = :noUtilisateur
        """;
    int nbRows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(utilisateur));
    if (nbRows != 1) {
      throw new RuntimeException("La modification du client a échouée : " + utilisateur);
    }
  }

  public void updateCredit(Utilisateur utilisateur) {
    String sql = """
        update utilisateurs
        set
        credit=:credit
        where no_utilisateur = :noUtilisateur
        """;
    int nbRows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(utilisateur));
    if (nbRows != 1) {
      throw new RuntimeException("La modification du client a échouée : " + utilisateur);
    }
  }

  @Override
  public void delete(int id) {
    String sql = "delete from utilisateurs where no_utilisateur = ? ";
    int nbRows = jdbcTemplate.update(sql, id);
    if (nbRows != 1) {
      throw new RuntimeException("La suppression de l'utilisateur a échouée : id= " + id);
    }
  }

  public Optional<Utilisateur> getByPseudo(String pseudo) {

    System.out.println("Recherche de l'utilisateur avec le pseudo : " + pseudo);

    String sql = "SELECT no_utilisateur AS noUtilisateur, pseudo, nom, prenom, email, telephone, rue,"
        + " code_postal AS codePostal, ville, mot_de_passe AS motDePasse, credit,"
        + " administrateur, desactive FROM utilisateurs WHERE pseudo = ? AND desactive = FALSE";

    Utilisateur utilisateur = null;
    try {
      utilisateur = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Utilisateur.class), pseudo);
    } catch (DataAccessException exc) {
      exc.printStackTrace();
      logger.warn("Utilisateur non trouvé : " + exc.getMessage());
    }

    return Optional.ofNullable(utilisateur);
  }

  public void desactiver(Boolean desactive, int id) {
    String sql = "UPDATE utilisateurs SET desactive = ? WHERE no_utilisateur = ?";
    jdbcTemplate.update(sql, desactive, id);
  }
}
