Feature: Gestion des utilisateurs
  Ajout d'un utilisateur
  Modification d'un utilisateur
  Suppression d'un utilisateur

  Scenario: Ajout d'un utilisateur
    Given un utilisateur :
    |nom      |prenom |age |role    |email                    |mot de passe |
    |tem      |bob    |18  |MANAGER |bob@outlook.fr           |password     |
    |pivot    |bernard|54  |VENDEUR |bernard.pivot@outlook.fr |password     |
    |sinclard |paul   |68  |VENDEUR |paul.sinclard@outlook.fr |password     |
    When je clique sur le bouton ajouter un utilisateur
    Then Je vérifie les entrées et j'ajoute l'utilisateur à la base de donnée

#  Scenario: Suppression d'un utilisteur
##    Given un utilisateur sélectionné à supprimer :
##      |nom      |prenom |age |role    |email                    |mot de passe |
##      |pivot    |bernard|54  |VENDEUR |bernard.pivot@outlook.fr |password     |
##    When je clique sur le bouton supprimé
##    Then je supprime l'utilisateur de la base de donnée
##
##  Scenario: Modification d'un utilisateur
##    Given un utilisateur sélectionné à modifier :
##      |nom      |prenom |age |role    |email                    |mot de passe |
##      |sinclard |paul   |68  |VENDEUR |paul.sinclard@outlook.fr |password     |
##    When je clique sur le bouton modifié
##    Then j'attend les modifications de l'utilisateur et je modifie l'utilisateur dans la base de donné

