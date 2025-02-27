package fr.campus.eni.encheres.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.campus.eni.encheres.bo.Utilisateur;
import fr.campus.eni.encheres.dal.UtilisateurRepositoryImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepositoryImpl utilisateurRepositoryImpl;

    public CustomUserDetailsService(UtilisateurRepositoryImpl utilisateurRepositoryImpl) {
        this.utilisateurRepositoryImpl = utilisateurRepositoryImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepositoryImpl.getByPseudo(username);

        if (!utilisateurOpt.isPresent()) {
            throw new UsernameNotFoundException("Utilisateur non trouvé pour le pseudo : " + username);
        }

        Utilisateur utilisateur = utilisateurOpt.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (Boolean.TRUE.equals(utilisateur.getAdministrateur())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(utilisateur.getPseudo(), utilisateur.getMotDePasse(), authorities);
    }
}
