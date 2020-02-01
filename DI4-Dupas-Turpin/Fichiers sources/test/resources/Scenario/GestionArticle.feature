Feature: Gestion des articles
  Ajout d'un article
  Modification d'un article
  Suppression d'un article

  private String nom;
  private String reference;
  private double prix;
  private int quantite;
  private String description;
  private InputStream photo;
  private Rayon rayon;

  Scenario: Ajout d'un article
    Given un article :
      |nom        |reference    |prix     |quantite |description  |photo      |rayon        |
      |article1   |9EAC8976ECA  |5148     |0        |description1 |null       |Rayon1       |
      |article2   |EB08CA97E80  |10.5     |150      |description2 |null       |Rayon2       |
      |article3   |0EACEA90C88  |844.99   |11       |description3 |null       |Rayon1       |
    When je clique sur le bouton ajouter un article


  Scenario: Suppression d'un article
    Given un article sélectionné à supprimer :
      |nom        |reference    |prix       |quantite |description  |photo      |rayon        |
      |article7   |9E7AE4F6ECA  |84.44      |1        |description  |null       |Rayon1       |
    When je clique sur le bouton supprimé l'article
    Then je supprime l'article de la base de donnée

  Scenario: Modification d'un article
    Given un article sélectionné à modifier :
      |nom        |reference    |prix     |quantite |description  |photo      |rayon        |
      |article8   |AE9ACE7C9A8  |2.6      |5        |description  |null       |Rayon1       |
    When je clique sur le bouton modifié l'article
    Then j'attend les modifications de l'article et je modifie l'article dans la base de donnée