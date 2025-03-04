package fr.campus.eni.encheres.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import io.github.cdimascio.dotenv.Dotenv;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;



@SpringBootTest  // Charge tout le contexte Spring Boot
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    static Dotenv dotenv;

  @BeforeAll
  static void setup() {
      dotenv = Dotenv.load();
      System.setProperty("DATASOURCE_URL", dotenv.get("DATASOURCE_URL"));
      System.setProperty("DATASOURCE_USERNAME", dotenv.get("DATASOURCE_USERNAME"));
      System.setProperty("DATASOURCE_PASSWORD", dotenv.get("DATASOURCE_PASSWORD"));
  }

    @Test
    public void testAfficherFormulaireLogin() throws Exception {
        
        mockMvc.perform(get("/login").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("pages/utilisateurs/formulaire-connexion"));
    }
}