BANKTERM - Système de Gestion Bancaire
📋 Description

BANKTERM est une application de gestion bancaire développée en Java utilisant JavaFX pour l'interface graphique. Ce projet implémente un système bancaire complet permettant aux utilisateurs de gérer leurs comptes, effectuer des transactions et consulter leur historique.
🏗️ Architecture

Le projet suit l'architecture MVC (Modèle-Vue-Contrôleur) :
📁 Structure du projet
text

src/main/java/com/javafx/
├── Models/
│   ├── Compte.java
│   ├── Deposit.java
│   ├── History.java
│   ├── User.java
│   ├── Virement.java
│   └── Retrait.java
├── Vue/
│   ├── Login.java
│   └── Dashboard.java
├── Controllers/
│   └── EventsControl.java
└── Main.java

✨ Fonctionnalités
🔐 Authentification

    Connexion : Accès sécurisé pour les utilisateurs existants

    Inscription : Création de nouveaux comptes bancaires

📊 Tableau de bord (Dashboard)

    Consultation du solde

    Dépôt d'argent

    Retrait d'argent

    Virement vers d'autres comptes

    Consultation de l'historique des transactions

🛠️ Technologies utilisées
Technologie	Usage
Java	Langage de programmation principal
JavaFX	Création d'interfaces graphiques
Architecture MVC	Séparation des préoccupations
JSON/GSON	Gestion des données (base non relationnelle)
Git/GitHub	Gestion de version et collaboration
🗂️ Modèles de données

Le système utilise les modèles suivants :

    Compte :

        Titulaire

        Numéro de compte

        Solde

        Code PIN

    Transaction :

        Dépôt (Deposit)

        Retrait (Retrait)

        Virement (Virement)

        Historique (History)

    Utilisateur (User) : Gestion des informations clients

🎮 Contrôleur

Le contrôleur gère la logique métier :

    Validation des données d'authentification

    Chargement des informations du compte

    Gestion des transactions (dépôt, retrait, virement)

    Communication entre les modèles et les vues

💾 Gestion des données

    Format : JSON avec la librairie GSON

    Structures : ArrayList et HashMap pour la manipulation des données

    Persistance : Sauvegarde des données dans des fichiers JSON

🚀 Installation et exécution
Prérequis

    JDK 8 ou supérieur

    Maven (optionnel)

    JavaFX SDK

Compilation et exécution
bash

# Compilation
javac -cp ".:javafx-sdk-*/lib/*" src/main/java/com/javafx/Main.java

# Exécution
java -cp ".:javafx-sdk-*/lib/*:src/main/java" com.javafx.Main

Avec Maven
bash

mvn clean compile
mvn javafx:run

📊 Interface utilisateur
Authentification

    Interface de connexion pour les utilisateurs existants

    Formulaire d'inscription pour les nouveaux clients

Dashboard

    Interface principale avec navigation intuitive

    Panneaux dédiés pour chaque opération bancaire

    Affichage en temps réel du solde et des transactions

🔄 Workflow des opérations

    Authentification → Vérification des identifiants

    Chargement → Récupération des données du compte

    Opération → Exécution de la transaction demandée

    Sauvegarde → Mise à jour des données en JSON

    Affichage → Mise à jour de l'interface

👥 Développement

    Méthodologie : Programmation Orientée Objet

    Gestion de version : Git avec GitHub

    Architecture : Modulaire avec séparation MVC

🎯 Objectifs pédagogiques

Ce projet a permis de :

    Maîtriser la programmation orientée objet en Java

    Comprendre et implémenter l'architecture MVC

    Développer des interfaces graphiques avec JavaFX

    Gérer la persistance des données avec JSON

    Collaborer efficacement avec Git/GitHub

📝 Notes

    Le projet utilise une approche modulaire pour faciliter la maintenance

    Les données sont stockées localement en format JSON

    L'interface suit les principes UX pour une expérience utilisateur optimale

🔮 Améliorations futures

    Implémentation d'une base de données relationnelle

    Ajout de fonctionnalités de sécurité avancées

    Développement d'une application mobile

    Intégration d'APIs bancaires externes

📄 Licence

Projet éducatif - Université de yaoundé 1
