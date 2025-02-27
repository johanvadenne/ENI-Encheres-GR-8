package fr.campus.eni.encheres.bll;

import fr.campus.eni.encheres.bo.Categorie;
import fr.campus.eni.encheres.dal.CategorieRepositoryImpl;
import fr.campus.eni.encheres.exceptions.ExeptionEchere;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CategorieServiceImpl implements ICrudService<Categorie> {

  private final CategorieRepositoryImpl CategorieRepositoryImpl;
  private final PasswordEncoder passwordEncoder;

  public CategorieServiceImpl(
      CategorieRepositoryImpl categorieRepositoryImpl, PasswordEncoder passwordEncoder) {
    this.CategorieRepositoryImpl = categorieRepositoryImpl;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void add(Categorie categorie) {
    CategorieRepositoryImpl.add(categorie);
  }

  @Override
  public List<Categorie> getAll() {
    return CategorieRepositoryImpl.getAll();
  }

  @Override
  public Optional<Categorie> getById(int id) {
    return CategorieRepositoryImpl.getById(id);
  }

  @Override
  public void update(Categorie client) {
    Optional<Categorie> clientOpt = getById(client.getNoCategorie());
    if (clientOpt.isPresent()) {
      CategorieRepositoryImpl.update(client);
    } else {
      // TODO gerer l'erreur
      throw new ExeptionEchere();
    }
  }

  @Override
  public void delete(int id) {
    CategorieRepositoryImpl.delete(id);
  }

  @Override
  public void save(Categorie entity) {

    if (entity.getNoCategorie() == null) {
      this.add(entity);
      return;
    }
    this.update(entity);
  }
}
