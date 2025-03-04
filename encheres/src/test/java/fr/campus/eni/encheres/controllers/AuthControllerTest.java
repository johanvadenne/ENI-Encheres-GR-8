package fr.campus.eni.encheres.controllers;

import fr.campus.eni.encheres.bll.UtilisateurServiceImpl;
import fr.campus.eni.encheres.bo.Utilisateur;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.cdimascio.dotenv.Dotenv;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // Désactive les filtres de sécurité dans le test
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static Dotenv dotenv;

    @BeforeAll
    static void setup() {
        dotenv = Dotenv.load(); // Charge le fichier .env automatiquement
        System.setProperty("DATASOURCE_URL", dotenv.get("DATASOURCE_URL"));
        System.setProperty("DATASOURCE_USERNAME", dotenv.get("DATASOURCE_USERNAME"));
        System.setProperty("DATASOURCE_PASSWORD", dotenv.get("DATASOURCE_PASSWORD"));
    }

    // On simule le service utilisé par le contrôleur
    @MockBean
    private UtilisateurServiceImpl utilisateurServiceImpl;

    @Test
    public void testAfficherFormulaireLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/utilisateurs/formulaire-connexion"));
    }

}
