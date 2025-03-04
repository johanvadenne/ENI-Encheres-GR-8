CREATE DATABASE encheres;

-- Création de la table CATEGORIES
CREATE TABLE CATEGORIES (
    no_categorie SERIAL PRIMARY KEY, -- SERIAL pour l'auto-incrémentation
    libelle      VARCHAR(30) NOT NULL
);

-- Création de la table UTILISATEURS
CREATE TABLE UTILISATEURS (
    no_utilisateur SERIAL PRIMARY KEY, -- SERIAL pour l'auto-incrémentation
    pseudo         VARCHAR(30) NOT NULL,
    nom            VARCHAR(30) NOT NULL,
    prenom         VARCHAR(30) NOT NULL,
    email          VARCHAR(50) NOT NULL,
    telephone      VARCHAR(15),
    rue            VARCHAR(30) NOT NULL,
    code_postal    VARCHAR(10) NOT NULL,
    ville          VARCHAR(50) NOT NULL,
    mot_de_passe   VARCHAR(256) NOT NULL,
    credit         INTEGER NOT NULL,
    administrateur BOOLEAN NOT NULL -- Utilisation de BOOLEAN pour le type bit
);

-- Création de la table ARTICLES_VENDUS
CREATE TABLE ARTICLES_VENDUS (
    no_article        SERIAL PRIMARY KEY, -- SERIAL pour l'auto-incrémentation
    nom_article       VARCHAR(30) NOT NULL,
    description       VARCHAR(300) NOT NULL,
    date_debut_encheres DATE NOT NULL,
    date_fin_encheres DATE NOT NULL,
    prix_initial      INTEGER,
    prix_vente        INTEGER,
    no_utilisateur    INTEGER NOT NULL,
    no_categorie      INTEGER NOT NULL,
    etatvente BOOLEAN DEFAULT FALSE,
    CONSTRAINT articles_vendus_categories_fk FOREIGN KEY (no_categorie) REFERENCES CATEGORIES (no_categorie) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT ventes_utilisateur_fk FOREIGN KEY (no_utilisateur) REFERENCES UTILISATEURS (no_utilisateur) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Création de la table RETRAITS
CREATE TABLE RETRAITS (
    no_article   INTEGER NOT NULL PRIMARY KEY,
    rue          VARCHAR(30) NOT NULL,
    code_postal  VARCHAR(15) NOT NULL,
    ville        VARCHAR(30) NOT NULL,
    CONSTRAINT retrait_article_fk FOREIGN KEY (no_article) REFERENCES ARTICLES_VENDUS (no_article) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- Création de la table ENCHERES
CREATE TABLE ENCHERES (
    no_enchere    SERIAL PRIMARY KEY, -- SERIAL pour l'auto-incrémentation
    date_enchere  TIMESTAMP NOT NULL, -- Utilisation de TIMESTAMP pour datetime
    montant_enchere INTEGER NOT NULL,
    no_article     INTEGER NOT NULL,
    no_utilisateur INTEGER NOT NULL,
    CONSTRAINT encheres_utilisateur_fk FOREIGN KEY (no_utilisateur) REFERENCES UTILISATEURS (no_utilisateur) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT encheres_no_article_fk FOREIGN KEY (no_article) REFERENCES ARTICLES_VENDUS (no_article) ON DELETE NO ACTION ON UPDATE NO ACTION
);


-- Ajout d'un utilisateur pour accès à la base de données posgresql (admin / admin)
DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'admin') THEN
        CREATE ROLE admin LOGIN PASSWORD 'admin';
    END IF;
END $$;

-- Attribution des privilèges sur les tables
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE CATEGORIES TO admin;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE UTILISATEURS TO admin;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE ARTICLES_VENDUS TO admin;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE RETRAITS TO admin;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE ENCHERES TO admin;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE utilisateurs_no_utilisateur_seq TO admin;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE articles_vendus_no_article_seq TO admin;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE encheres_no_enchere_seq TO admin;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE categories_no_categorie_seq TO admin;
