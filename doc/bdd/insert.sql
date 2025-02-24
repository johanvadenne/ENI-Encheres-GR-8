INSERT INTO CATEGORIES (libelle) VALUES
('Informatique'),
('Vêtements'),
('Mobilier'),
('Livres'),
('Électronique');


INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES
('john_doe', 'Doe', 'John', 'john.doe@example.com', '0123456789', '1 Rue de Paris', '75000', 'Paris', 'password123', 1000, FALSE),
('jane_smith', 'Smith', 'Jane', 'jane.smith@example.com', '0987654321', '5 Avenue des Champs', '75008', 'Paris', 'password456', 1500, FALSE),
('admin_user', 'Admin', 'Admin', 'admin@example.com', '0000000000', '10 Boulevard Saint-Germain', '75005', 'Paris', 'admin123', 5000, TRUE),
('alice_brown', 'Brown', 'Alice', 'alice.brown@example.com', '0147258632', '25 Rue de Lyon', '69000', 'Lyon', 'alicepassword', 2000, FALSE),
('bob_jones', 'Jones', 'Bob', 'bob.jones@example.com', '0765432109', '40 Rue de Marseille', '13000', 'Marseille', 'bobpassword', 800, FALSE);

INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES
('Ordinateur Portable', 'Ordinateur portable 15 pouces, 8Go RAM, 256Go SSD', '2025-03-01', '2025-03-15', 500, NULL, 1, 1),
('T-shirt Homme', 'T-shirt 100% coton, taille M', '2025-02-25', '2025-03-05', 20, NULL, 2, 2),
('Canapé 3 places', 'Canapé en tissu, couleur gris', '2025-03-10', '2025-03-20', 250, NULL, 4, 3),
('Livre Python', 'Apprenez la programmation Python', '2025-02-28', '2025-03-10', 30, NULL, 5, 4),
('Casque Audio', 'Casque sans fil, réduction de bruit active', '2025-03-05', '2025-03-15', 150, NULL, 1, 5);


INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES
(1, '1 Rue de Paris', '75000', 'Paris'),
(2, '5 Avenue des Champs', '75008', 'Paris'),
(3, '25 Rue de Lyon', '69000', 'Lyon'),
(4, '10 Boulevard Saint-Germain', '75005', 'Paris'),
(5, '40 Rue de Marseille', '13000', 'Marseille');


INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur) VALUES
('2025-03-01 10:00:00', 550, 1, 2),
('2025-03-02 12:00:00', 25, 2, 3),
('2025-03-05 14:30:00', 275, 3, 4),
('2025-02-28 16:00:00', 35, 4, 5),
('2025-03-06 11:00:00', 175, 5, 2);
