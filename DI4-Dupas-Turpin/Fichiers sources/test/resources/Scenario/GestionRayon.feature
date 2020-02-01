Feature: Gestion des rayons
  Ajout d'un rayon
  Modification d'un rayon
  Suppression d'un rayon

  Scenario: Ajout d'un rayon
    Given un rayon :
      |nom        |responsable  |
      |Natation   |Roger        |
      |Tennis     |Adrien       |
      |Equitation |Yoann        |
    When je clique sur le bouton ajouter un rayon
    Then Je vérifie les entrées et j'ajoute le rayon à la base de donnée

  Scenario: Suppression d'un rayon
    Given un rayon sélectionné à supprimer :
      |nom             |magasin     |responsable  |
      |Basketball      |Magasin1    |Sardoche     |
    When je clique sur le bouton supprimé le rayon
    Then je supprime le rayon de la base de donnée

  Scenario: Modification d'un rayon
    Given un rayon sélectionné à modifier :
      |nom          |magasin     |responsable |
      |Fitness      |Magasin1    |Carl        |
    When je clique sur le bouton modifié le rayon
    Then j'attend les modifications du rayon et je modifie le rayon dans la base de donnée