package fr.campus.eni.encheres;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Servir le dossier "static" par défaut
    registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

    // Servir le dossier "D:/uploads/"
    registry.addResourceHandler("/images-enchere/**").addResourceLocations("file:D:/uploads/");
  }
}
