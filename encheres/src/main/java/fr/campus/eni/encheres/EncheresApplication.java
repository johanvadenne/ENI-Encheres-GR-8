package fr.campus.eni.encheres;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableScheduling
public class EncheresApplication implements CommandLineRunner {

    private final Dotenv dotenv;

    public EncheresApplication(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.load();
    System.setProperty("DATASOURCE_URL", dotenv.get("DATASOURCE_URL"));
    System.setProperty("DATASOURCE_USERNAME", dotenv.get("DATASOURCE_USERNAME"));
    System.setProperty("DATASOURCE_PASSWORD", dotenv.get("DATASOURCE_PASSWORD"));
    SpringApplication.run(EncheresApplication.class, args);
  }

  
  @Override
  public void run(String... args) {
    Dotenv dotenv = Dotenv.load();
    System.setProperty("DATASOURCE_URL", dotenv.get("DATASOURCE_URL"));
    System.setProperty("DATASOURCE_USERNAME", dotenv.get("DATASOURCE_USERNAME"));
    System.setProperty("DATASOURCE_PASSWORD", dotenv.get("DATASOURCE_PASSWORD"));
  }
}
