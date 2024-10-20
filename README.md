# Gestion des Commandes en Ligne

## Description

Ce projet vise à développer une application web sécurisée permettant aux clients de passer des commandes et aux administrateurs de gérer les utilisateurs et les produits. L'application utilise une architecture en couches (MVC) et inclut une gestion des utilisateurs avec différents rôles, ainsi que des fonctionnalités CRUD pour les produits et les commandes.

## Table des Matières

- [Objectifs](#objectifs)
- [Pages Principales](#pages-principales)
- [Structure des Classes](#structure-des-classes)
- [Exigences Techniques](#exigences-techniques)
- [Concepts Java Avancés](#concepts-java-avancés)
- [Installation](#installation)
- [Tests](#tests)
- [Contributions](#contributions)
- [License](#license)

## Objectifs

- Implémenter une gestion des utilisateurs avec authentification par session.
- Utiliser Thymeleaf comme moteur de template.
- Gérer les produits, les commandes et les utilisateurs via des fonctionnalités CRUD.
- Implémenter des tests unitaires avec JUnit et Mockito.
- Appliquer une architecture en couches (MVC).

## Pages Principales

1. **Page d'authentification**
2. **Page de gestion des produits (Admin uniquement)**
   - Liste des produits avec pagination et recherche.
   - Création, modification et suppression des produits.
3. **Page de gestion des commandes (Admin et Client)**
   - Liste des commandes avec pagination et recherche.
   - Fonctionnalités spécifiques pour les clients et les admins.
4. **Page de gestion des utilisateurs (Admin uniquement)**
   - Liste des utilisateurs avec pagination et recherche.
   - Création, modification et suppression des utilisateurs.

## Structure des Classes

- **Utilisateur**: `nom`, `prénom`, `email`, `motDePasse`, `rôle` (Enum: Admin, Client)
- **Admin**: `niveauAcces`
- **Client**: `adresseLivraison`, `moyenPaiement`
- **Produit**: `nom`, `description`, `prix`, `stock`
- **Commande**: `dateCommande`, `statut` (Enum: en attente, en traitement, expédiée, livrée, annulée)

## Structure détaillée
```
├── src
│   └── main
│       └── java
│           └── com
│               └── huongdanjava
│                   └── oms
│                       ├── controller
│                       │   ├── AdminController.java
│                       │   ├── AuthenticationController.java
│                       │   ├── ClientController.java
│                       │   ├── OrderController.java
│                       │   ├── ProductController.java
│                       │   └── UserController.java
│                       │
│                       ├── dao
│                       │   ├── implementation
│                       │   │   ├── OrderDAOImpl.java
│                       │   │   ├── ProductDAOImpl.java
│                       │   │   └── UserDAOImpl.java
│                       │   │
│                       │   └── Interface
│                       │       ├── OrderDAO.java
│                       │       ├── ProductDAO.java
│                       │       └── UserDAO.java
│                       │
│                       ├── model
│                       │   ├── Admin.java
│                       │   ├── AdminType.java
│                       │   ├── Client.java
│                       │   ├── Order.java
│                       │   ├── Product.java
│                       │   ├── Role.java
│                       │   └── User.java
│                       │
│                       ├── service
│                       |   ├── OrderService.java
|                       |   ├── ProductService.java
|                       |   └── UserService.java
|                       |
|                       ├── util
|                       |   ├── LoggerUtil.java
|                       |   ├── PersistenceUtil.java
|                       |   ├── ThymeleafUtil.java
```
## Exigences Techniques

- Développement en Java 8.
- Utilisation de Thymeleaf pour la création des vues.
- Gestion des dépendances avec Maven.
- Conteneur web Tomcat.
- Tests unitaires avec JUnit et Mockito en TDD.
- Architecture MVC avec les design patterns Repository et Singleton.
- Base de données PostgreSQL avec JPA et Hibernate.
- Gestion du projet avec JIRA et méthode Scrum.

## Diagramme 
### Diagramme de class
![dclassGestCommand](https://github.com/user-attachments/assets/1a31ab65-92ef-4ecb-b975-e2d9fd11fc27)
### Diagramme de cas d'utilisation
![Diagramme vierge](https://github.com/user-attachments/assets/95e9b7d4-cbce-4451-b195-9058759557ae)

## Pages 
### Login page
![Screenshot from 2024-10-19 16-23-51](https://github.com/user-attachments/assets/37a1f2c4-f786-4e0e-a7f2-2dcf71755996)
### Liste des produits pour les admins
![Screenshot from 2024-10-19 16-01-07](https://github.com/user-attachments/assets/35f5d0c9-c706-47b4-9674-dce408bc8205)
### Liste des clients pour les admins
![Screenshot from 2024-10-20 14-54-08](https://github.com/user-attachments/assets/27acbc27-d14d-4b97-8410-1de5847a6dba)
### Liste des admins 
![Screenshot from 2024-10-20 14-54-20](https://github.com/user-attachments/assets/0de4a0d9-55b4-40d4-a8d8-cd533cd6938d)


### Liste des produits pour les clients
![Screenshot from 2024-10-20 14-41-25](https://github.com/user-attachments/assets/1f724040-900c-486c-9d53-17e7a57830f0)


## Concepts Java Avancés

- Java Time API
- Collection API
- Hashmaps
- Lambda Expressions
- Java Stream API
- Optional

## Installation

#### 1. Cloner le repository :
   ```bash
   git clone https://github.com/Application-Web-de-Blog/oms.git
   ```
#### 2.Accéder au dossier du projet :
``` bash
cd oms
```
#### 3.Installer les dépendances :
``` bash
mvn install
```
#### 4.Déployer le fichier WAR sur Tomcat.

## Tests
Pour exécuter les tests, utilisez :

```bash
mvn test
```

