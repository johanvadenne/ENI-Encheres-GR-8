package fr.campus.eni.encheres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.campus.eni.encheres.bll.EnchereServiceImpl;

@Component
public class EnchereScheduler {
    @Autowired
    private EnchereServiceImpl enchereService;

    @Scheduled(cron = "*/1 * * * * *") // Exécute tous les jours à minuit
    public void cloturerEncheres() {
        System.out.println("Cloture des encheres");
        enchereService.verifierEtCloturerEncheres();
    }
}
