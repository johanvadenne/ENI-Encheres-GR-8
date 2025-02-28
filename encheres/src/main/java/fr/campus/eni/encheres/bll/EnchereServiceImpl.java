package fr.campus.eni.encheres.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.campus.eni.encheres.bo.ArticleVendu;
import fr.campus.eni.encheres.bo.Enchere;
import fr.campus.eni.encheres.bo.Utilisateur;
import fr.campus.eni.encheres.dal.ArticleVenduRepositoryImpl;
import fr.campus.eni.encheres.dal.EnchereRepositoryImpl;
import fr.campus.eni.encheres.dal.UtilisateurRepositoryImpl;
import fr.campus.eni.encheres.exceptions.ExeptionEchere;

@Service
public class EnchereServiceImpl implements ICrudService<Enchere> {

    private final EnchereRepositoryImpl EnchereRepositoryImpl;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ArticleVenduRepositoryImpl articleRepository;

    @Autowired
    private UtilisateurRepositoryImpl UtilisateurRepository;

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

    public List<Enchere> getEnhereValidByNoUtilisateur(Integer noUtilisateur) {
        return EnchereRepositoryImpl.getEnhereValidByNoUtilisateur(noUtilisateur);
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

    @Transactional
    public void verifierEtCloturerEncheres() {
        List<ArticleVendu> articlesACloturer = articleRepository.findByDateFinEncheres();

        for (ArticleVendu article : articlesACloturer) {

            if (article.getEtatVente() != true) {
                Optional<Enchere> meilleureEnchereOpt = EnchereRepositoryImpl.getLastEnchereByArticle(article.getNoArticle());

                if (meilleureEnchereOpt.isPresent()) {
                    Enchere meilleureEnchere = meilleureEnchereOpt.get();
                    Utilisateur vendeur = article.getVendeur();
                    Utilisateur acheteur = UtilisateurRepository.getById(meilleureEnchere.getNoUtilisateur()).get();

                    vendeur.setCredit(vendeur.getCredit() + meilleureEnchere.getMontantEnchere());
                    article.setPrixVente(meilleureEnchere.getMontantEnchere());
                    acheteur.setCredit(acheteur.getCredit() - meilleureEnchere.getMontantEnchere());
                    article.setEtatVente(true);

                    UtilisateurRepository.updateCredit(vendeur);
                    UtilisateurRepository.updateCredit(acheteur);
                    articleRepository.update(article);

                } else {
                    article.setPrixVente(0);
                    article.setEtatVente(true);
                    articleRepository.update(article);
                }
            }
        }
    }

    public Optional<Enchere> getLastEnchereByArticle(Integer noArticle) {
        return EnchereRepositoryImpl.getLastEnchereByArticle(noArticle);
    }
}
