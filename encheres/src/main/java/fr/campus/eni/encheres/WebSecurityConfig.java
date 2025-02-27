package fr.campus.eni.encheres;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
        (requests) -> requests
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/login", "/logout", "/register", "/images/**")
            .permitAll()
            .anyRequest()
            .authenticated())
        .formLogin(
            form -> form.loginPage("/login")
                .loginProcessingUrl("/auth/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/", true)
                .permitAll())
        .logout((logout) -> logout.permitAll());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Utilisation de BCrypt pour encoder les mots de passe
  }

  // @Bean
  // public UserDetailsService userDetailsService() {
  // UserDetails user = User.withDefaultPasswordEncoder()
  // .username("user")
  // .password("password")
  // .roles("USER")
  // .build();

  // return new InMemoryUserDetailsManager(user);
  // }
}
