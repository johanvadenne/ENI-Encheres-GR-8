package fr.campus.eni.bll;

import java.util.List;
import java.util.Optional;

import fr.campus.eni.bo.Utilisateur;
import fr.campus.eni.dal.UtilisateurRepositoryImpl;
import fr.campus.eni.exceptions.ExeptionEchere;

public class UtilisateurServiceImpl implements ICrudService<Utilisateur> {

    private final UtilisateurRepositoryImpl UtilisateurRepositoryImpl;

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

        if (entity.getNoUtilisateur() == null) {
            this.add(entity);
            return;
        }
        this.update(entity);

    }
}
