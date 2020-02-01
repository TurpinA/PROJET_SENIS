package model;

public class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private int age;
    private Role role;
    private String mail;
    private String motDePasse;

    public Utilisateur(){

    }

    public Utilisateur(String nom, String prenom, Role role, int age, String mail, String motDePasse){
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.role = role;
        this.mail = mail;
        this.motDePasse = motDePasse;
    }

    public Rayon rayon = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Rayon getRayon() {
        return rayon;
    }

    public void setRayon(Rayon rayon) {
        this.rayon = rayon;
    }

    @Override
    public String toString(){
        return prenom + " " + nom;
    }
}
