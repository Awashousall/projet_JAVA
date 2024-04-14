import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.Comparator;


public class InterfaceGestionEmprunts extends JFrame implements ActionListener {
    // Déclaration des boutons
    private JButton btnEmprunter;
    private JButton btnRetourner;
    //private JButton btnRechercherEmprunt;
    private JButton btnListerEmprunts;
    private JButton btnRapportStatistique;
    private JButton btnRetour;

    // Listes pour stocker les emprunts et les livres empruntés par chaque utilisateur
    private List<Emprunt> listeEmprunts;
    private Map<String, List<String>> empruntsUtilisateurs;
    //private static List<Emprunt> listeEmprunts;


    public InterfaceGestionEmprunts() {
        setTitle("Gestion des Emprunts et Retours");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Initialisation des boutons
        btnEmprunter = new JButton("Emprunter un livre");
        btnRetourner = new JButton("Retourner un livre");
        btnListerEmprunts = new JButton("Lister les emprunts");
        btnRapportStatistique = new JButton("Rapport Statistique");
        btnRetour = new JButton("Retour");

        // Ajout des écouteurs d'événements aux boutons
        btnEmprunter.addActionListener(this);
        btnRetourner.addActionListener(this);
        btnListerEmprunts.addActionListener(this);
        btnRapportStatistique.addActionListener(this);
        btnRetour.addActionListener(this);

        // Création du panneau de boutons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.add(btnEmprunter);
        panel.add(btnRetourner);
        panel.add(btnListerEmprunts);
        panel.add(btnRapportStatistique);
        panel.add(btnRetour);

        // Ajout du panneau au contenu de la fenêtre
        add(panel);

        // Rendre la fenêtre visible
        setVisible(true);

        // Initialisation des listes d'emprunts et de livres empruntés
        listeEmprunts = new ArrayList<>();
        empruntsUtilisateurs = new HashMap<>();

        // Chargement des emprunts au démarrage de l'application
    listeEmprunts = chargerEmprunts();
    if (listeEmprunts == null) {
        listeEmprunts = new ArrayList<>();
    }
    }

    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnEmprunter) {
        gererEmprunt();
    } else if (e.getSource() == btnRetourner) {
        gererRetour();
    }  else if (e.getSource() == btnListerEmprunts) {
        listerEmprunts();
    } else if (e.getSource() == btnRapportStatistique) {
      genererRapportStatistique();
    }else if (e.getSource() == btnRetour) {
        sauvegarderEmprunts(listeEmprunts); // Sauvegarder la liste des emprunts
         new Bibliotheque();
        dispose();
    }
}

public void creerBibliothequeAvecEmprunts() {
    List<Emprunt> listeEmprunts = chargerEmprunts();
    new Bibliotheque(listeEmprunts);
}


private void genererRapportStatistique() {
    Map<String, Integer> statistiquesLivres = new HashMap<>();

    // Calculer les statistiques
    for (Emprunt emprunt : listeEmprunts) {
        String titreLivre = emprunt.getTitreLivre();
        statistiquesLivres.put(titreLivre, statistiquesLivres.getOrDefault(titreLivre, 0) + 1);
    }

    // Trier les livres en fonction du nombre d'emprunts
    List<Map.Entry<String, Integer>> listeTriee = new ArrayList<>(statistiquesLivres.entrySet());
    listeTriee.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    // Afficher le rapport
    StringBuilder rapport = new StringBuilder();
    rapport.append("Rapport Statistique sur les Livres les Plus Empruntés :\n\n");
    int count = 1;
    for (Map.Entry<String, Integer> entry : listeTriee) {
        rapport.append(count).append(". ").append(entry.getKey()).append(" - ").append(entry.getValue()).append(" emprunts\n");
        count++;
    }

    // Afficher le rapport dans une boîte de dialogue
    JOptionPane.showMessageDialog(null, rapport.toString(), "Rapport Statistique", JOptionPane.INFORMATION_MESSAGE);
}





   private void listerEmprunts() {
        // Créer un tableau pour stocker les données de la table
        Object[][] data = new Object[listeEmprunts.size()][3];
        int i = 0;
        for (Emprunt emprunt : listeEmprunts) {
            data[i][0] = emprunt.getNomUtilisateur();
            data[i][1] = emprunt.getTitreLivre();
            data[i][2] = emprunt.getDateEmprunt();
            i++;
        }

        // Créer les titres des colonnes
        String[] columnNames = {"Nom Utilisateur", "Titre Livre", "Date Emprunt"};

        // Créer le modèle de table avec les données et les titres des colonnes
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Créer une JTable avec le modèle de table
        JTable table = new JTable(model);

        // Ajouter la table à une JScrollPane pour permettre le défilement si nécessaire
        JScrollPane scrollPane = new JScrollPane(table);

        // Créer une nouvelle fenêtre pour afficher la table
        JFrame frame = new JFrame("Liste des Emprunts");
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


 // Méthode pour gérer l'emprunt d'un livre
   private void gererEmprunt() {
    String nomUtilisateur = JOptionPane.showInputDialog("Entrez votre nom :");
    if (nomUtilisateur != null && !nomUtilisateur.isEmpty()) {
        // Vérifier le nombre d'emprunts actuels de l'utilisateur
        int nombreEmprunts = compterEmpruntsUtilisateur(nomUtilisateur);
        if (nombreEmprunts < 2) {
            String cotisationStr = JOptionPane.showInputDialog("Entrez le montant de la cotisation (2000) :");
            // Vérification si la cotisation saisie est correcte
            if (cotisationStr != null && !cotisationStr.isEmpty()) {
                int cotisation = Integer.parseInt(cotisationStr);
                if (cotisation == 2000) {
                    String titreLivre = JOptionPane.showInputDialog("Entrez le titre du livre à emprunter :");
                    if (titreLivre != null && !titreLivre.isEmpty()) {
                        if (utilisateurExiste(nomUtilisateur)) {
                            if (livreExiste(titreLivre)) {
                                List<Livres> livres = chargerLivres();
                                for (Livres livre : livres) {
                                    if (livre.getTitre().equalsIgnoreCase(titreLivre)) {
                                        if (livre.getNbExemplaires() > 0) {
                                            // Il reste des exemplaires disponibles
                                            String dateEmprunt = LocalDate.now().toString();
                                            ajouterEmprunt(nomUtilisateur, titreLivre, dateEmprunt);
                                            livre.setNbExemplaires(livre.getNbExemplaires() - 1); // Décrémenter le nombre d'exemplaires
                                            sauvegarderLivres(livres); // Mettre à jour la liste des livres
                                            listeEmprunts.add(new Emprunt(nomUtilisateur, titreLivre, dateEmprunt)); // Ajouter l'emprunt à la liste des emprunts
                                            JOptionPane.showMessageDialog(null, "Livre emprunté avec succès !");
                                            listerEmprunts(); // Appeler la méthode pour lister les emprunts après chaque ajout d'emprunt
                                        } else {
                                            // Aucun exemplaire disponible
                                            JOptionPane.showMessageDialog(null, "Désolé, ce livre n'est pas disponible pour le moment.");
                                        }
                                        return; // Sortir de la boucle une fois que le livre est trouvé
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Le livre spécifié n'existe pas.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Utilisateur non trouvé.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer un titre de livre valide.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Solde insuffisant. Vous devez payer une cotisation de 2000.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez entrer un montant de cotisation.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vous avez déjà emprunté 2 livres. Vous ne pouvez pas emprunter plus.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez entrer un nom d'utilisateur valide.");
    }
}

private int compterEmpruntsUtilisateur(String nomUtilisateur) {
    int nombreEmprunts = 0;
    for (Emprunt emprunt : listeEmprunts) {
        if (emprunt.getNomUtilisateur().equalsIgnoreCase(nomUtilisateur)) {
            nombreEmprunts++;
        }
    }
    return nombreEmprunts;
}


// Méthode pour gérer le retour d'un livre
     private void gererRetour() {
    String nomUtilisateur = JOptionPane.showInputDialog(null, "Entrez le nom de l'utilisateur :");
    String titreLivre = JOptionPane.showInputDialog(null, "Entrez le titre du livre à retourner :");

    // Vérification si l'utilisateur existe
    if (utilisateurExiste(nomUtilisateur)) {
        // Vérification si le livre existe
        if (livreExiste(titreLivre)) {
            // Simulation du retour (remplacez ceci par votre propre logique)
            for (Emprunt emprunt : listeEmprunts) {
                if (emprunt.getNomUtilisateur().equals(nomUtilisateur) && emprunt.getTitreLivre().equals(titreLivre)) {
                    listeEmprunts.remove(emprunt); // Retirer l'emprunt de la liste
                    JOptionPane.showMessageDialog(null, "Livre retourné avec succès !");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Ce livre n'a pas été emprunté par cet utilisateur.");
        } else {
            JOptionPane.showMessageDialog(null, "Le livre n'existe pas.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "L'utilisateur n'existe pas.");
    }
}

    // Méthode pour charger la liste des livres depuis un fichier
    private List<Livres> chargerLivres() {
    List<Livres> livres = null;
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("livres.ser"))) {
        livres = (List<Livres>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        // Gérer l'exception en conséquence, par exemple en retournant une liste vide
    }
    return livres; // Retourne la liste de livres chargée, ou null en cas d'erreur
}
  
  // Méthode pour sauvegarder la liste des livres dans un fichier
private void sauvegarderLivres(List<Livres> livres) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("livres.ser"))) {
            oos.writeObject(livres);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
     // Méthode pour ajouter un emprunt à la liste des emprunts
    private void ajouterEmprunt(String nomUtilisateur, String titreLivre, String dateEmprunt) {
    }
     
     // Méthode pour vérifier si un utilisateur existe dans la liste des utilisateurs
    private boolean utilisateurExiste(String nomUtilisateur) {
    List<Utilisateur> utilisateurs = Utilisateur.getUtilisateurs();
    for (Utilisateur utilisateur : utilisateurs) {
        if (utilisateur.getNom().equalsIgnoreCase(nomUtilisateur)) {
            return true;
        }
    }
    return false; // Retourne false si aucun utilisateur correspondant n'est trouvé
}
 
 // Méthode pour vérifier si un livre existe dans la liste des livres
private boolean livreExiste(String titreLivre) {
    List<Livres> livres = chargerLivres();
    for (Livres livre : livres) {
        if (livre.getTitre().equalsIgnoreCase(titreLivre)) {
            return true;
        }
    }
    return false; // Retourne false si aucun livre correspondant n'est trouvé
}

// Méthode pour charger la liste des emprunts depuis un fichier
private List<Emprunt> chargerEmprunts() {
    List<Emprunt> emprunts = null;
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("emprunts.ser"))) {
        emprunts = (List<Emprunt>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        // Gérer l'exception en conséquence, par exemple en retournant une liste vide
    }
    return emprunts; // Retourne la liste d'emprunts chargée, ou null en cas d'erreur
}
  
  // Méthode pour sauvegarder la liste des emprunts dans un fichier
private void sauvegarderEmprunts(List<Emprunt> emprunts) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("emprunts.ser"))) {
        oos.writeObject(emprunts);
    } catch (IOException e) {
        e.printStackTrace();
        // Gérer l'exception en conséquence
    }
}


    public static void main(String[] args) {
        InterfaceGestionEmprunts interfaceGestionEmprunts = new InterfaceGestionEmprunts();
    interfaceGestionEmprunts.creerBibliothequeAvecEmprunts();
        new InterfaceGestionEmprunts();
    }
}
