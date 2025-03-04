package fr.campus.eni.encheres;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootTest
class EncheresApplicationTests {

  static Dotenv dotenv;

  @BeforeAll
  static void setup() {
      dotenv = Dotenv.load();
      System.setProperty("DATASOURCE_URL", dotenv.get("DATASOURCE_URL"));
      System.setProperty("DATASOURCE_USERNAME", dotenv.get("DATASOURCE_USERNAME"));
      System.setProperty("DATASOURCE_PASSWORD", dotenv.get("DATASOURCE_PASSWORD"));
  }

  @Test
  void contextLoads() {}
}
