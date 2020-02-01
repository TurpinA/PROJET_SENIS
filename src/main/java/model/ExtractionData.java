package model;

import controler.Connexion;
import jdk.jshell.execution.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class ExtractionData {

    public static final String LOGGER = "logger";
    public static Connexion connexion = null;

    private ExtractionData() {throw new IllegalStateException("Utility class");}

    public static Rayon rechercheRayonParResponsable(Utilisateur utilisateur) throws SQLException {

        Rayon rayon = null;
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM rayon WHERE utilisateur_ID = " + utilisateur.getId())) {
            if (result.next()) {
                rayon = new Rayon();
                rayon.setId(result.getInt(1));
                rayon.setNom(result.getString(2));
                rayon.setMagasin(null);
                rayon.setResponsable(utilisateur);
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        connexion.stopConnexion();

        return rayon;
    }

    public static Rayon rechercheRayonParID(int rayonID) throws SQLException {

        Rayon rayon = null;
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM rayon INNER JOIN utilisateur ON rayon.utilisateur_ID = utilisateur.ID WHERE rayon.ID = " + rayonID)) {
            if (result.next()) {
                rayon = new Rayon();
                rayon.setId(result.getInt(1));
                rayon.setNom(result.getString(2));
                rayon.setMagasin(null);
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(result.getInt(5));
                utilisateur.setNom(result.getString(6));
                utilisateur.setPrenom(result.getString(7));
                utilisateur.setAge(result.getInt(8));
                utilisateur.setRole(Role.valueOf(result.getString(9)));
                utilisateur.setMail(result.getString(10));
                utilisateur.setMotDePasse(result.getString(11));

                rayon.setResponsable(utilisateur);
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }

        connexion.stopConnexion();

        return rayon;
    }

    public static Rayon rechercheRayonParNom(String rayonNom) throws SQLException {

        Rayon rayon = null;
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM rayon INNER JOIN utilisateur ON rayon.utilisateur_ID = utilisateur.ID WHERE rayon.nom = '" + rayonNom + "'")) {
            if (result.next()) {
                rayon = new Rayon();
                rayon.setId(result.getInt(1));
                rayon.setNom(result.getString(2));
                rayon.setMagasin(null);
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(result.getInt(5));
                utilisateur.setNom(result.getString(6));
                utilisateur.setPrenom(result.getString(7));
                utilisateur.setAge(result.getInt(8));
                utilisateur.setRole(Role.valueOf(result.getString(9)));
                utilisateur.setMail(result.getString(10));
                utilisateur.setMotDePasse(result.getString(11));

                rayon.setResponsable(utilisateur);
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }

        connexion.stopConnexion();

        return rayon;
    }

    public static List<Article> rechercheAllArticleParRayon(Rayon rayon) throws SQLException {

        ArrayList<Article> articleList = new ArrayList<>();
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM article WHERE rayon_ID = " + rayon.getId())) {
            while (result.next()) {
                Article article = new Article();
                article.setId(result.getInt(1));
                article.setNom(result.getString(2));
                article.setReference(result.getString(3));
                article.setPrix(result.getDouble(4));
                article.setQuantite(result.getInt(5));
                article.setDescription(result.getString(6));
                article.setPhoto(null);
                article.setRayon(rayon);

                articleList.add(article);
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        connexion.stopConnexion();

        return articleList;
    }

    public static Article rechercheArticleParReference(String reference) throws SQLException {

        Article article = null;
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM article INNER JOIN rayon ON article.rayon_ID = rayon.ID WHERE article.reference = '" + reference + "'")) {
            if(result.next()) {
                article = new Article();
                article.setId(result.getInt(1));
                article.setNom(result.getString(2));
                article.setReference(result.getString(3));
                article.setPrix(result.getDouble(4));
                article.setQuantite(result.getInt(5));
                article.setDescription(result.getString(6));
                article.setPhoto(null);

                Rayon rayon = new Rayon();
                rayon.setId(result.getInt(9));
                rayon.setNom(result.getString(10));

                article.setRayon(rayon);
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        connexion.stopConnexion();

        return article;
    }

    public static List<Rayon> rechercheAllRayon() throws SQLException {

        ArrayList<Rayon> rayonList = new ArrayList<>();
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM rayon LEFT OUTER JOIN utilisateur ON rayon.utilisateur_ID = utilisateur.ID")) {

            while (result.next()) {
                Rayon rayon = new Rayon();
                rayon.setId(result.getInt(1));
                rayon.setNom(result.getString(2));
                rayon.setMagasin(null);

                if (result.getInt(5) != 0 && result.getString(6) != null && Role.valueOf(result.getString(9)) != null) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setId(result.getInt(5));
                    utilisateur.setNom(result.getString(6));
                    utilisateur.setPrenom(result.getString(7));
                    utilisateur.setAge(result.getInt(8));
                    utilisateur.setRole(Role.valueOf(result.getString(9)));
                    utilisateur.setMail(result.getString(10));
                    utilisateur.setMotDePasse(result.getString(11));

                    rayon.setResponsable(utilisateur);
                }
                rayonList.add(rayon);
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        connexion.stopConnexion();

        return rayonList;
    }

    public static List<Utilisateur> rechercheAllUtilisateur() throws SQLException {

        ArrayList<Utilisateur> utilisateurList = new ArrayList<>();
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM utilisateur")) {
            while (result.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(result.getInt(1));
                utilisateur.setNom(result.getString(2));
                utilisateur.setPrenom(result.getString(3));
                utilisateur.setAge(result.getInt(4));
                utilisateur.setRole(Role.valueOf(result.getString(5)));
                utilisateur.setMail(result.getString(6));
                utilisateur.setMotDePasse(result.getString(7));

                utilisateurList.add(utilisateur);
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }

        connexion.stopConnexion();

        return utilisateurList;
    }

    public static Utilisateur rechercheUtilisateurParMail(String mail) throws SQLException {

        Utilisateur utilisateur = null;
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM utilisateur WHERE email = '" + mail + "'")) {
            if(result.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(result.getInt(1));
                utilisateur.setNom(result.getString(2));
                utilisateur.setPrenom(result.getString(3));
                utilisateur.setAge(result.getInt(4));
                utilisateur.setRole(Role.valueOf(result.getString(5)));
                utilisateur.setMail(result.getString(6));
                utilisateur.setMotDePasse(result.getString(7));
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        connexion.stopConnexion();

        return utilisateur;
    }

    public static Utilisateur rechercheUtilisateurParID(int utilisateurID) throws SQLException {

        Utilisateur utilisateur = null;
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM utilisateur WHERE ID = " + utilisateurID)) {
            if(result.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(result.getInt(1));
                utilisateur.setNom(result.getString(2));
                utilisateur.setPrenom(result.getString(3));
                utilisateur.setAge(result.getInt(4));
                utilisateur.setRole(Role.valueOf(result.getString(5)));
                utilisateur.setMail(result.getString(6));
                utilisateur.setMotDePasse(result.getString(7));
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        connexion.stopConnexion();
        if(utilisateur.getId() == 0){
            return null;
        }else{
            return utilisateur;
        }

    }

    public static Article rechercheArticleParID(int articleID) throws SQLException {

        Article article = null;
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt.executeQuery("SELECT * FROM article INNER JOIN rayon ON article.rayon_ID = rayon.ID WHERE article.ID = " + articleID)) {
            if(result.next()) {
                article = new Article();
                article.setId(result.getInt(1));
                article.setNom(result.getString(2));
                article.setReference(result.getString(3));
                article.setPrix(result.getDouble(4));
                article.setQuantite(result.getInt(5));
                article.setDescription(result.getString(6));
                article.setPhoto(null);

                Rayon rayon = new Rayon();
                rayon.setId(result.getInt(9));
                rayon.setNom(result.getString(10));

                article.setRayon(rayon);
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        connexion.stopConnexion();

        return article;
    }
}
