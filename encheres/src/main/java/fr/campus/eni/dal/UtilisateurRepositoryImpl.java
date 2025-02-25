package fr.campus.eni.dal;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import fr.campus.eni.bo.Utilisateur;

public class UtilisateurRepositoryImpl implements ICrudRepository<Utilisateur>{
    Logger logger = LoggerFactory.getLogger(UtilisateurRepositoryImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public UtilisateurRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }



    @Override
    public void add(Utilisateur unUtilisateur) {
        String sql = "insert into encheres (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)"
				+ " values (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse, :credit, :administrateur)";
		namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(unUtilisateur));
    }

    @Override
    public List<Utilisateur> getAll() {
        String sql = "select no_utilisateur as noUtilisateur, nom, prenom, email, telephone, rue, code_postal as codePostal, ville, mot_de_passe as motDePasse, credit, administrateur"
				+ " from utilisateurs";
		List<Utilisateur> utilisateurs = namedParameterJdbcTemplate.query(sql,
				new BeanPropertyRowMapper<>(Utilisateur.class));
				
		return utilisateurs;
    }

    @Override
    public Optional<Utilisateur> getById(int id) {
        String sql = "select no_utilisateur as noUtilisateur, nom, prenom, email, telephone, rue, code_postal as codePostal, ville, mot_de_passe as motDePasse, credit, administrateur"
				+ " from utilisateurs where no_utilisateur = ?";
        Utilisateur utilisateur = null;
		try {
            utilisateur = jdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper<>(Utilisateur.class), id);				
		}catch(DataAccessException exc) {
			exc.printStackTrace();
			logger.warn(exc.getMessage());
		}
				
		return Optional.ofNullable(utilisateur);
    }

    @Override
    public void update(Utilisateur utilisateur) {
        String sql = "update utilisateurs set nom=:nom, prenom=:prenom, email=:email, telephone=:telephone, "
				+ "rue=:rue, code_postal=:codePostal, ville=:ville, mot_de_passe=:motDePasse, credit=:credit, administrateur=:administrateur where no_utilisateur = :noUtilisateur";
		int nbRows = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(utilisateur));
		if(nbRows != 1) {
			throw new RuntimeException("La modification du client a échouée : " + utilisateur );
		}
    }

    @Override
    public void delete(int id) {
        String sql = "delete from utilisateurs where no_utilisateur = ? ";
		int nbRows = jdbcTemplate.update(sql, id);
		if(nbRows != 1) {
			throw new RuntimeException("La suppression de l'utilisateur a échouée : id= " +id );
		}
    }

}
