import javax.swing.JOptionPane;
import java.io.Serializable;

public class Essaie extends Livres implements Ouvrages, Serializable {
    private String sujet;

    public Essaie() {}

    public Essaie(String titre, String auteur, int anneePublication, int nbExemplaires, String sujet) {
        super(titre, auteur, anneePublication, nbExemplaires);
        this.sujet = sujet;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    @Override
    public void ajouter() {
        String titre = JOptionPane.showInputDialog("Donner le titre de l'essai : ");
        String auteur = JOptionPane.showInputDialog("Donner l'auteur de l'essai : ");
        int anneePublication = Integer.parseInt(JOptionPane.showInputDialog("Donner l'année de publication de l'essai : "));
        int nbExemplaires = Integer.parseInt(JOptionPane.showInputDialog("Donner le nombre d'exemplaires de l'essai à ajouter : "));
        String sujet = JOptionPane.showInputDialog("Donner le sujet de l'essai : ");

        Essaie nouvelEssai = new Essaie(titre, auteur, anneePublication, nbExemplaires, sujet);
        ajouterLivre(nouvelEssai);
    }

    @Override
    public void supprimer() {
        String sujetRecherche = JOptionPane.showInputDialog("Entrez le sujet de l'essai à supprimer :");
        supprimerParSujet(sujetRecherche);
    }

    private void supprimerParSujet(String sujetRecherche) {
        List<Livres> listeLivres = chargerLivres();
        List<Livres> essaisASupprimer = new ArrayList<>();

        for (Livres livre : listeLivres) {
            if (livre instanceof Essaie) {
                Essaie essai = (Essaie) livre;
                if (essai.getSujet().equalsIgnoreCase(sujetRecherche)) {
                    essaisASupprimer.add(essai);
                }
            }
        }

        if (!essaisASupprimer.isEmpty()) {
            listeLivres.removeAll(essaisASupprimer);
            sauvegarderLivres(listeLivres);
            JOptionPane.showMessageDialog(null, "Essai(s) supprimé(s) avec succès !");
            rafraichirTableau();
        } else {
            JOptionPane.showMessageDialog(null, "Aucun essai trouvé pour ce sujet.");
        }
    }

    @Override
    public void modifier() {
        String sujetRecherche = JOptionPane.showInputDialog("Entrez le sujet de l'essai à modifier :");
        modifierParSujet(sujetRecherche);
    }

    private void modifierParSujet(String sujetRecherche) {
        List<Livres> listeLivres = chargerLivres();
        Essaie essaiAModifier = null;

        for (Livres livre : listeLivres) {
            if (livre instanceof Essaie) {
                Essaie essai = (Essaie) livre;
                if (essai.getSujet().equalsIgnoreCase(sujetRecherche)) {
                    essaiAModifier = essai;
                    break;
                }
            }
        }

        if (essaiAModifier != null) {
            String nouveauTitre = JOptionPane.showInputDialog("Entrez le nouveau titre de l'essai :");
            String nouvelAuteur = JOptionPane.showInputDialog("Entrez le nouvel auteur de l'essai :");
            int nouvelleAnnee = Integer.parseInt(JOptionPane.showInputDialog("Entrez la nouvelle année de publication de l'essai :"));
            int nouveauNbExemplaires = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau nombre d'exemplaires de l'essai :"));
            String nouveauSujet = JOptionPane.showInputDialog("Entrez le nouveau sujet de l'essai :");

            essaiAModifier.setTitre(nouveauTitre);
            essaiAModifier.setAuteur(nouvelAuteur);
            essaiAModifier.setAnneePublication(nouvelleAnnee);
            essaiAModifier.setNbExemplaires(nouveauNbExemplaires);
            essaiAModifier.setSujet(nouveauSujet);

            sauvegarderLivres(listeLivres);
            JOptionPane.showMessageDialog(null, "Essai modifié avec succès !");
            rafraichirTableau();
        } else {
            JOptionPane.showMessageDialog(null, "Aucun essai trouvé pour ce sujet.");
        }
    }
}
