package main;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.List;

import static org.junit.Assert.*;

public class GestionUtilisateurTest {

    //|nom      |prenom |age |role    |email                    |mot de passe | id (generated) |
    //|tem      |bob    |18  |MANAGER |bob@outlook.fr           |password     | 1              |
    //|pivot    |bernard|54  |VENDEUR |bernard.pivot@outlook.fr |password     | 2              |
    //|sinclard |paul   |68  |VENDEUR |paul.sinclard@outlook.fr |password     | 3              |

    @Given("un utilisateur :")
    public void un_utilisateur(io.cucumber.datatable.DataTable dataTable) {
        List<String> list = dataTable.asList(String.class);

        throw new io.cucumber.java.PendingException();
    }

    @When("je clique sur le bouton ajouter un utilisateur")
    public void je_clique_sur_le_bouton_ajouter_un_utilisateur() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("Je vérifie les entrées et j'ajoute l'utilisateur à la base de donnée")
    public void je_vérifie_les_entrées_et_j_ajoute_l_utilisateur_à_la_base_de_donnée() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}