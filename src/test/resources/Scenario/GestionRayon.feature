Feature: Gestion des rayons
  Ajout d'un rayon
  Modification d'un rayon
  Suppression d'un rayon

  Scenario: Ajout d'un rayon
    Given un rayon :
      |nom      |magasin |responsable|
      |Natation      |Magasin1    |Roger  |
      |Tennis    |Magasin1|Adrien  |
      |Equitation |Magasin1   |Yoann  |
    When je clique sur le bouton ajouter un rayon
    Then Je vérifie les entrées et j'ajoute le rayon à la base de donnée

  Scenario: Suppression d'un rayon
    Given un rayon sélectionné à supprimer :
      |nom      |magasin |responsable|
      |Natation      |Magasin1    |Roger  |
    When je clique sur le bouton supprimé
    Then je supprime le rayon de la base de donnée

  Scenario: Modification d'un rayon
    Given un rayon sélectionné à modifier :
      |nom      |magasin |responsable|
      |Natation      |Magasin1    |Roger  |
    When je clique sur le bouton modifié
    Then j'attend les modifications du rayon et je modifie le rayon dans la base de donné