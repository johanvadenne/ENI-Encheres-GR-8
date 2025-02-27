package fr.campus.eni.encheres.bll;

import fr.campus.eni.encheres.bo.Retrait;
import fr.campus.eni.encheres.dal.RetraitRepositoryImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RetraitServiceImpl implements ICrudService<Retrait> {

  private final RetraitRepositoryImpl RetraitRepositoryImpl;
  private final PasswordEncoder passwordEncoder;

  public RetraitServiceImpl(
      RetraitRepositoryImpl retraitRepositoryImpl, PasswordEncoder passwordEncoder) {
    this.RetraitRepositoryImpl = retraitRepositoryImpl;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void add(Retrait retrait) {
    RetraitRepositoryImpl.add(retrait);
  }

  @Override
  public List<Retrait> getAll() {
    return RetraitRepositoryImpl.getAll();
  }

  @Override
  public Optional<Retrait> getById(int id) {
    return RetraitRepositoryImpl.getById(id);
  }

  @Override
  public void update(Retrait client) {
    // Optional<Retrait> clientOpt = getById(client.getNoRetrait());
    // if (clientOpt.isPresent()) {
    //     RetraitRepositoryImpl.update(client);
    // } else {
    //     //TODO gerer l'erreur
    //     throw new ExeptionEchere();
    // }
  }

  @Override
  public void delete(int id) {
    RetraitRepositoryImpl.delete(id);
  }

  @Override
  public void save(Retrait entity) {
    this.add(entity);
    this.update(entity);
  }
}
