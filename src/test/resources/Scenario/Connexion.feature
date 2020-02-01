Feature: Connexion a l'application

  Scenario: Connexion avec ses identifiants a l'application dans la page d'acceuil
    Given des identifiants :
      |mail                             |motdepasse          |
      |alexandre@outlook.com            |password            |
    When je clique sur le bouton connexion
    Then Je me connecte à l'application