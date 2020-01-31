package main;

import controler.Connexion;
import controler.ControlerAjouterRayon;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Magasin;
import model.Rayon;
import model.Role;
import model.Utilisateur;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionRayonTest {

    private List<Utilisateur>  ArrayListRayon = new ArrayList<>();
    private List<Utilisateur> ExtractRayon;

    //|nom      |magasin |responsable|
    //|Natation      |Magasin1    |Roger  |
    //|Tennis    |Magasin1|Adrien  |
    //|Equitation |Magasin1   |Yoann  |

    @Before
    public void beforeScenario() throws SQLException, FileNotFoundException {
        String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/?serverTimezone=UTC&useSSL=false";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
        ScriptRunner sr = new ScriptRunner(con);
        sr.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/sql/baseTest.sql"));
        sr.runScript(reader);
        Connexion connexion = new Connexion();
        connexion.setConnexion("127.0.0.1","3306","baseTest", "root", "",connexion);
    }

    @Given("un rayon :")
    public void un_rayon(io.cucumber.datatable.DataTable dataTable) throws SQLException {

    }
    @When("je clique sur le bouton ajouter un rayon")
    public void je_clique_sur_le_bouton_ajouter_un_rayon() throws SQLException {
        throw new io.cucumber.java.PendingException();
    }
    @Then("Je vérifie les entrées et j'ajoute le rayon à la base de donnée")
    public void je_vérifie_les_entrées_et_j_ajoute_le_rayon_à_la_base_de_donnée() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("un rayon sélectionné à supprimer :")
    public void un_rayon_sélectionné_à_supprimer(io.cucumber.datatable.DataTable dataTable) {

        throw new io.cucumber.java.PendingException();
    }
    @When("je clique sur le bouton supprimé")
    public void je_clique_sur_le_bouton_supprimé() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("je supprime le rayon de la base de donnée")
    public void je_supprime_le_rayon_de_la_base_de_donnée() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("un rayon sélectionné à modifier :")
    public void un_rayon_sélectionné_à_modifier(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        throw new io.cucumber.java.PendingException();
    }
    @When("je clique sur le bouton modifié")
    public void je_clique_sur_le_bouton_modifié() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("j'attend les modifications du rayon et je modifie le rayon dans la base de donné")
    public void j_attend_les_modifications_du_rayon_et_je_modifie_le_rayon_dans_la_base_de_donné() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


}