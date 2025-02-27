package fr.campus.eni.encheres.bll;

import fr.campus.eni.encheres.bo.ArticleVendu;
import fr.campus.eni.encheres.dal.ArticleVenduRepositoryImpl;
import fr.campus.eni.encheres.exceptions.ExeptionEchere;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ArticleVenduServiceImpl implements ICrudService<ArticleVendu> {

  private final ArticleVenduRepositoryImpl ArticleVenduRepositoryImpl;
  private final PasswordEncoder passwordEncoder;

  public ArticleVenduServiceImpl(
      ArticleVenduRepositoryImpl articleVenduRepositoryImpl, PasswordEncoder passwordEncoder) {
    this.ArticleVenduRepositoryImpl = articleVenduRepositoryImpl;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void add(ArticleVendu articleVendu) {
    ArticleVenduRepositoryImpl.add(articleVendu);
  }

  @Override
  public List<ArticleVendu> getAll() {
    return ArticleVenduRepositoryImpl.getAll();
  }

  @Override
  public Optional<ArticleVendu> getById(int id) {
    return ArticleVenduRepositoryImpl.getById(id);
  }

  @Override
  public void update(ArticleVendu client) {
    Optional<ArticleVendu> clientOpt = getById(client.getNoArticle());
    if (clientOpt.isPresent()) {
      ArticleVenduRepositoryImpl.update(client);
    } else {
      // TODO gerer l'erreur
      throw new ExeptionEchere();
    }
  }

  @Override
  public void delete(int id) {
    ArticleVenduRepositoryImpl.delete(id);
  }

  @Override
  public void save(ArticleVendu entity) {

    if (entity.getNoArticle() == null) {
      this.add(entity);
      return;
    }
    this.update(entity);
  }
}
