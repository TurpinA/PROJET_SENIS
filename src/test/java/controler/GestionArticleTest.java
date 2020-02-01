package controler;

import controler.ActionBD;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jdk.jshell.execution.Util;
import model.Article;
import model.Rayon;
import model.Role;
import model.Utilisateur;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GestionArticleTest {

    private List<Article> ArrayListArticle = new ArrayList<>();
    private List<Article> ExtractArticle = new ArrayList<>();

    @Before
    public void before(Scenario scenario) throws SQLException, FileNotFoundException {
        String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/?serverTimezone=UTC&useSSL=false";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
        ScriptRunner sr = new ScriptRunner(con);
        sr.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/sql/viderLaBase.sql"));
        sr.runScript(reader);
    }
    @Given("un article :")
    public void un_article(io.cucumber.datatable.DataTable dataTable) throws SQLException {
        List<Map<String,String>> list = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> map : list) {

            Rayon rayon = new Rayon();
            rayon.setNom(map.get("rayon"));
            rayon.setResponsable(null);

            Utilisateur utilisateur = new Utilisateur();

            rayon = ActionBD.createRayon(rayon.getNom(),utilisateur);

            Article article = ActionBD.createArticle(map.get("nom"), map.get("reference"), Double.valueOf(map.get("prix")),Integer.valueOf(map.get("quantite")),map.get("description"),null,rayon);

            ArrayListArticle.add(article);
        }
    }

    @When("je clique sur le bouton ajouter un article")
    public void je_clique_sur_le_bouton_ajouter_un_article() throws SQLException {
        for (Article article : ArrayListArticle) {
            ExtractArticle.add(ActionBD.createArticle(article.getNom(),article.getReference(),article.getPrix(),article.getQuantite(),article.getDescription(),null,article.getRayon()));
        }
    }
    @Then("Je vérifie les entrées et j'ajoute l'article à la base de donnée")
    public void je_vérifie_les_entrées_et_j_ajoute_l_article_à_la_base_de_donnée() throws SQLException {
        for (Article article : ExtractArticle) {
            assertNotNull(model.ExtractionData.rechercheArticleParID(article.getId()));
            assertNotNull(model.ExtractionData.rechercheArticleParReference(article.getReference()));
        }
    }

    @Given("un article sélectionné à supprimer :")
    public void un_article_sélectionné_à_supprimer(io.cucumber.datatable.DataTable dataTable) throws SQLException {
        List<Map<String,String>> list = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> map : list) {

            Rayon rayon = new Rayon();
            rayon.setNom(map.get("rayon"));
            rayon.setResponsable(null);

            Utilisateur utilisateur = new Utilisateur();

            rayon = ActionBD.createRayon(rayon.getNom(),utilisateur);

            Article article = ActionBD.createArticle(map.get("nom"), map.get("reference"), Double.valueOf(map.get("prix")),Integer.valueOf(map.get("quantite")),map.get("description"),null,rayon);
            ExtractArticle.clear();
            ExtractArticle.add(article);
        }
    }

    @When("je clique sur le bouton supprimé l'article")
    public void je_clique_sur_le_bouton_supprimé_l_article() throws SQLException {
        ActionBD.supprimerArticle(ExtractArticle.get(0));
    }
    @Then("je supprime l'article de la base de donnée")
    public void je_supprime_l_article_de_la_base_de_donnée() throws SQLException {
        assertNull(model.ExtractionData.rechercheArticleParID(ExtractArticle.get(0).getId()));
    }

    @Given("un article sélectionné à modifier :")
    public void un_article_sélectionné_à_modifier(io.cucumber.datatable.DataTable dataTable) throws SQLException {
        List<Map<String,String>> list = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> map : list) {

            Rayon rayon = new Rayon();
            rayon.setNom(map.get("rayon"));
            rayon.setResponsable(null);

            Utilisateur utilisateur = new Utilisateur();

            rayon = ActionBD.createRayon(rayon.getNom(),utilisateur);

            Article article = ActionBD.createArticle(map.get("nom"), map.get("reference"), Double.valueOf(map.get("prix")),Integer.valueOf(map.get("quantite")),map.get("description"),null,rayon);
            ExtractArticle.clear();
            ExtractArticle.add(article);
        }
    }

    @When("je clique sur le bouton modifié l'article")
    public void je_clique_sur_le_bouton_modifié_l_article() throws SQLException {
        Article articleModifie = ExtractArticle.get(0);
        articleModifie.setNom("NomModifié");
        ActionBD.modifierArticle(articleModifie);
    }
    @Then("j'attend les modifications de l'article et je modifie l'article dans la base de donnée")
    public void j_attend_les_modifications_de_l_article_et_je_modifie_l_article_dans_la_base_de_donnée() throws SQLException {
        assertEquals(model.ExtractionData.rechercheArticleParID(ExtractArticle.get(0).getId()).getNom(),"NomModifié");
    }

}
