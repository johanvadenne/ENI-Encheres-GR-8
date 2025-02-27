package fr.campus.eni.encheres.bll;

import fr.campus.eni.encheres.bo.Enchere;
import fr.campus.eni.encheres.dal.EnchereRepositoryImpl;
import fr.campus.eni.encheres.exceptions.ExeptionEchere;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EnchereServiceImpl implements ICrudService<Enchere> {

  private final EnchereRepositoryImpl EnchereRepositoryImpl;
  private final PasswordEncoder passwordEncoder;

  public EnchereServiceImpl(
      EnchereRepositoryImpl enchereRepositoryImpl, PasswordEncoder passwordEncoder) {
    this.EnchereRepositoryImpl = enchereRepositoryImpl;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void add(Enchere enchere) {
    EnchereRepositoryImpl.add(enchere);
  }

  @Override
  public List<Enchere> getAll() {
    return EnchereRepositoryImpl.getAll();
  }

  public List<Enchere> getByNoArticle(Integer noArticle) {
    return EnchereRepositoryImpl.getByNoArticle(noArticle);
  }

  @Override
  public Optional<Enchere> getById(int id) {
    return EnchereRepositoryImpl.getById(id);
  }

  @Override
  public void update(Enchere client) {
    Optional<Enchere> clientOpt = getById(client.getNoEnchere());
    if (clientOpt.isPresent()) {
      EnchereRepositoryImpl.update(client);
    } else {
      // TODO gerer l'erreur
      throw new ExeptionEchere();
    }
  }

  @Override
  public void delete(int id) {
    EnchereRepositoryImpl.delete(id);
  }

  @Override
  public void save(Enchere entity) {

    if (entity.getNoEnchere() == null) {
      this.add(entity);
      return;
    }
    this.update(entity);
  }
}
