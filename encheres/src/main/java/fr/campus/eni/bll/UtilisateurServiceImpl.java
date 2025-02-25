package fr.campus.eni.bll;

import java.util.List;
import java.util.Optional;

import fr.campus.eni.bo.Utilisateur;
import fr.campus.eni.dal.UtilisateurRepositoryImpl;
import fr.campus.eni.exceptions.ExeptionEchere;

public class UtilisateurServiceImpl implements ICrudService<Utilisateur> {

    private final UtilisateurRepositoryImpl UtilisateurRepositoryImpl;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UtilisateurServiceImpl(
        UtilisateurRepositoryImpl utilisateurRepositoryImpl,
        PasswordEncoder passwordEncoder
    ) {
        this.utilisateurRepositoryImpl = utilisateurRepositoryImpl;
        this.passwordEncoder = passwordEncoder;
    }


    public UtilisateurServiceImpl(fr.campus.eni.dal.UtilisateurRepositoryImpl utilisateurRepositoryImpl) {
        UtilisateurRepositoryImpl = utilisateurRepositoryImpl;
    }

    @Override
    public void add(Utilisateur utilisateur) {
        UtilisateurRepositoryImpl.add(utilisateur);
    }

    @Override
    public List<Utilisateur> getAll() {
        return UtilisateurRepositoryImpl.getAll();
    }

    @Override
    public Optional<Utilisateur> getById(int id) {
        return UtilisateurRepositoryImpl.getById(id);
    }

    public void update(Utilisateur client) {
        Optional<Utilisateur> clientOpt = getById(client.getNoUtilisateur());
        if (clientOpt.isPresent()) {
            UtilisateurRepositoryImpl.update(client);
        } else {
            //TODO gerer l'erreur
            throw new ExeptionEchere();
        }

    }

    public void delete(int id) {
        UtilisateurRepositoryImpl.delete(id);
    }

    @Override
    public void save(Utilisateur entity) {
        // On vérifie si c'est une création
        if (entity.getNoUtilisateur() == null) {
            // Hachage
            if (entity.getMotDePasse() != null) {
                String hashed = passwordEncoder.encode(entity.getMotDePasse());
                entity.setMotDePasse(hashed);
            }
            this.add(entity);
        } else {
            this.update(entity);
        }
    }
}
