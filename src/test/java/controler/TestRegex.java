package controler;

import controler.ControlerAjouterArticle;
import controler.ControlerAjouterRayon;
import controler.ControlerAjouterUtilisateur;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRegex {

    private ControlerAjouterArticle controlerAjouterArticle = new ControlerAjouterArticle();
    private ControlerAjouterRayon controlerAjouterRayon = new ControlerAjouterRayon();
    private ControlerAjouterUtilisateur controlerAjouterUtilisateur = new ControlerAjouterUtilisateur();

    @Test
    public void test_regex_ajouter_article_nom(){
        assertTrue(controlerAjouterArticle.testRegexNom("Nike Air Max"));
        assertTrue(controlerAjouterArticle.testRegexNom("Test-tet ee"));
        assertTrue(controlerAjouterArticle.testRegexNom("Lee-Van's 2"));

        assertFalse(controlerAjouterArticle.testRegexNom(""));
        assertFalse(controlerAjouterArticle.testRegexNom("?eac"));
        assertFalse(controlerAjouterArticle.testRegexNom("!:)="));
    }

    @Test
    public void test_regex_ajouter_article_reference(){
        assertTrue(controlerAjouterArticle.testRegexReference("AE894EA874C"));
        assertTrue(controlerAjouterArticle.testRegexReference("AECAEC"));
        assertTrue(controlerAjouterArticle.testRegexReference("4874"));

        assertFalse(controlerAjouterArticle.testRegexReference("AE894E/A8?74C"));
        assertFalse(controlerAjouterArticle.testRegexReference("AE894 EA874C"));
        assertFalse(controlerAjouterArticle.testRegexReference("AE894-EA874C"));
        assertFalse(controlerAjouterArticle.testRegexReference(""));
    }

    @Test
    public void test_regex_ajouter_article_prix(){
        assertTrue(controlerAjouterArticle.testRegexPrix("10.3"));
        assertTrue(controlerAjouterArticle.testRegexPrix("10"));
        assertTrue(controlerAjouterArticle.testRegexPrix("10."));
        assertTrue(controlerAjouterArticle.testRegexPrix("0"));

        assertFalse(controlerAjouterArticle.testRegexPrix("-10"));
        assertFalse(controlerAjouterArticle.testRegexPrix("-10.3"));
        assertFalse(controlerAjouterArticle.testRegexPrix("10?"));
        assertFalse(controlerAjouterArticle.testRegexPrix("10.3.9"));
        assertFalse(controlerAjouterArticle.testRegexPrix("aec"));
        assertFalse(controlerAjouterArticle.testRegexPrix("10,3"));
        assertFalse(controlerAjouterArticle.testRegexPrix("10a"));
    }

    @Test
    public void test_regex_ajouter_article_quantite(){
        assertTrue(controlerAjouterArticle.testRegexQuantite("10"));
        assertTrue(controlerAjouterArticle.testRegexQuantite("0"));

        assertFalse(controlerAjouterArticle.testRegexQuantite("-10"));
        assertFalse(controlerAjouterArticle.testRegexQuantite("-10.3"));
        assertFalse(controlerAjouterArticle.testRegexQuantite("10."));
        assertFalse(controlerAjouterArticle.testRegexQuantite("10.3"));
        assertFalse(controlerAjouterArticle.testRegexQuantite("10?"));
        assertFalse(controlerAjouterArticle.testRegexQuantite("10.3.9"));
        assertFalse(controlerAjouterArticle.testRegexQuantite("aec"));
        assertFalse(controlerAjouterArticle.testRegexQuantite("10,3"));
        assertFalse(controlerAjouterArticle.testRegexQuantite("10a"));
    }

    @Test
    public void test_regex_ajouter_rayon_nom(){
        assertTrue(controlerAjouterRayon.testRegexNom("Nike Air Max"));
        assertTrue(controlerAjouterRayon.testRegexNom("Test-tet ee"));
        assertTrue(controlerAjouterRayon.testRegexNom("Lee-Van's 2"));

        assertFalse(controlerAjouterRayon.testRegexNom(""));
        assertFalse(controlerAjouterRayon.testRegexNom("?eac"));
        assertFalse(controlerAjouterRayon.testRegexNom("!:)="));
    }

    @Test
    public void test_regex_ajouter_utilisateur_nom(){
        assertTrue(controlerAjouterUtilisateur.testRegexNom("Alexandre"));
        assertTrue(controlerAjouterUtilisateur.testRegexNom("Jean-Pierre"));
        assertTrue(controlerAjouterUtilisateur.testRegexNom("Jean Pierre"));

        assertFalse(controlerAjouterUtilisateur.testRegexNom("Alexandre2"));
        assertFalse(controlerAjouterUtilisateur.testRegexNom(""));
        assertFalse(controlerAjouterUtilisateur.testRegexNom("?eac"));
        assertFalse(controlerAjouterUtilisateur.testRegexNom("!:)="));
    }

    @Test
    public void test_regex_ajouter_utilisateur_prenom(){
        assertTrue(controlerAjouterUtilisateur.testRegexPrenom("Alexandre"));
        assertTrue(controlerAjouterUtilisateur.testRegexPrenom("Jean-Pierre"));
        assertTrue(controlerAjouterUtilisateur.testRegexPrenom("Jean Pierre"));

        assertFalse(controlerAjouterUtilisateur.testRegexPrenom("Alexandre2"));
        assertFalse(controlerAjouterUtilisateur.testRegexPrenom(""));
        assertFalse(controlerAjouterUtilisateur.testRegexPrenom("?eac"));
        assertFalse(controlerAjouterUtilisateur.testRegexPrenom("!:)="));
    }

    @Test
    public void test_regex_ajouter_utilisateur_age(){
        assertTrue(controlerAjouterUtilisateur.testRegexAge("10"));
        assertTrue(controlerAjouterUtilisateur.testRegexAge("0"));

        assertFalse(controlerAjouterUtilisateur.testRegexAge("-10"));
        assertFalse(controlerAjouterUtilisateur.testRegexAge("-10.3"));
        assertFalse(controlerAjouterUtilisateur.testRegexAge("10."));
        assertFalse(controlerAjouterUtilisateur.testRegexAge("10.3"));
        assertFalse(controlerAjouterUtilisateur.testRegexAge("10?"));
        assertFalse(controlerAjouterUtilisateur.testRegexAge("10.3.9"));
        assertFalse(controlerAjouterUtilisateur.testRegexAge("aec"));
        assertFalse(controlerAjouterUtilisateur.testRegexAge("10,3"));
        assertFalse(controlerAjouterUtilisateur.testRegexAge("10a"));
    }

    @Test
    public void test_regex_ajouter_utilisateur_mail(){
        assertTrue(controlerAjouterUtilisateur.testRegexMail("alexandre@mail"));
        assertTrue(controlerAjouterUtilisateur.testRegexMail("alexandre@mail.fr"));
        assertTrue(controlerAjouterUtilisateur.testRegexMail("alexandre?./.32&'@mail.fr"));
        assertTrue(controlerAjouterUtilisateur.testRegexMail("@"));
        assertTrue(controlerAjouterUtilisateur.testRegexMail("-10"));

        assertFalse(controlerAjouterUtilisateur.testRegexMail(""));
    }

    @Test
    public void test_regex_ajouter_utilisateur_unique_arobase(){
        assertTrue(controlerAjouterUtilisateur.uniqueArobase("alexandre@mail"));
        assertTrue(controlerAjouterUtilisateur.uniqueArobase("alexandre@mail.fr"));
        assertTrue(controlerAjouterUtilisateur.uniqueArobase("alexandre?./.32&'@mail.fr"));
        assertTrue(controlerAjouterUtilisateur.uniqueArobase("@"));

        assertFalse(controlerAjouterUtilisateur.uniqueArobase("alexandre@@mail.fr"));
        assertFalse(controlerAjouterUtilisateur.uniqueArobase("ale@xandre@mail.fr"));
        assertFalse(controlerAjouterUtilisateur.uniqueArobase("abeciabcei"));
        assertFalse(controlerAjouterUtilisateur.uniqueArobase("-10"));
        assertFalse(controlerAjouterUtilisateur.uniqueArobase(""));

    }
}

