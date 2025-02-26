package fr.campus.eni.encheres.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.campus.eni.encheres.bll.UtilisateurServiceImpl;
import fr.campus.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private UtilisateurServiceImpl UtilisateurServiceImpl;

    UtilisateurController(UtilisateurServiceImpl UtilisateurServiceImpl) {
        this.UtilisateurServiceImpl = UtilisateurServiceImpl;
    }
}