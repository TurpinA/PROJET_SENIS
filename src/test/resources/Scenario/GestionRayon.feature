Feature: Gestion des rayons
  Ajout d'un rayon
  Modification d'un rayon
  Suppression d'un rayon

  Scenario: Ajout d'un rayon
    Given un rayon :
      ||
    When je clique sur le bouton ajouter un rayon
    Then Je vérifie les entrées et j'ajoute le rayon à la base de donnée

  Scenario: Suppression d'un rayon
    Given un rayon sélectionné à supprimer
    When je clique sur le bouton supprimé
    Then je supprime le rayon de la base de donnée

  Scenario: Modification d'un rayon
    Given un rayon sélectionné à modifier
    When je clique sur le bouton modifié
    Then j'attend les modifications du rayon et je modifie le rayon dans la base de donné