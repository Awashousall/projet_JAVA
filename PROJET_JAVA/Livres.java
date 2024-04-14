import javax.swing.JOptionPane;
import java.io.Serializable;

public class Livres implements Ouvrages, Serializable {
    private String titre;
    private String auteur;
    private int anneePublication;
    private int nbExemplaires;
    private String numeroISBN;
    private String type;

    private static int compteurISBN = 1;

    public Livres() {}

    public Livres(String titre, String auteur, int anneePublication, int nbExemplaires, String type) {
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.nbExemplaires = nbExemplaires;
        this.numeroISBN = generateISBN(); // Générer le numéro ISBN lors de la création de l'objet
        this.type = type;
    }

    // Ajoutez les getters et setters nécessaires

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    public int getNbExemplaires() {
        return nbExemplaires;
    }

    public void setNbExemplaires(int nbExemplaires) {
        this.nbExemplaires = nbExemplaires;
    }

    public String getNumeroISBN() {
        return numeroISBN;
    }

    public void setNumeroISBN(String numeroISBN) {
        this.numeroISBN = numeroISBN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Méthode pour générer un numéro ISBN
    private String generateISBN() {
        return "ISBN-" + String.format("%03d", compteurISBN++);
    }

    @Override
    public void ajouter() {
        // L'ajout est désormais géré dans l'interface graphique
    }

    @Override
    public void supprimer() {
        // La suppression est désormais gérée dans l'interface graphique
    }

    @Override
    public void modifier() {
        // La modification est désormais gérée dans l'interface graphique
    }

    // Méthode pour afficher les informations du livre dans une boîte de dialogue
    public void afficheLivre() {
        JOptionPane.showMessageDialog(null,
                "Titre : " + titre + "\n" +
                "Auteur : " + auteur + "\n" +
                "Année de publication : " + anneePublication + "\n" +
                "Nombre d'exemplaires : " + nbExemplaires + "\n" +
                "Numéro ISBN : " + numeroISBN);
    }

    // Méthode pour obtenir les données du livre sous forme de tableau
    public String[][] getLivreData() {
        String[][] data = new String[1][5]; // Un seul livre, donc un seul tableau de données
        data[0][0] = getTitre();
        data[0][1] = getAuteur();
        data[0][2] = String.valueOf(getAnneePublication());
        data[0][3] = String.valueOf(getNbExemplaires());
        data[0][4] = getNumeroISBN();
        return data;
    }

    @Override
    public String toString() {
        return "Titre: " + titre + "\n" +
                "Auteur: " + auteur + "\n" +
                "Année de publication: " + anneePublication + "\n" +
                "Nombre d'exemplaires: " + nbExemplaires + "\n" +
                "Numéro ISBN: " + numeroISBN;
    }

    // Méthode pour rechercher un livre par auteur
    public boolean rechercherParAuteur(String auteurRecherche) {
        return this.auteur.equalsIgnoreCase(auteurRecherche);
    }

    // Méthode pour rechercher un livre par numéro ISBN
    public boolean rechercherParISBN(String isbnRecherche) {
        return this.numeroISBN.equals(isbnRecherche);
    }
}
