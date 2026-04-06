package fr.ul.miashs.compil.tds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table extends HashMap<String, List<Symbole>>{

    // Recherche d’un symbole par son nom dans la table.
    // Si plusieurs symboles portent le même nom, on retourne le premier
    public Symbole rechercherSymbole(String nom) {
        List<Symbole> liste = this.get(nom);
        if (liste == null || liste.isEmpty()) return null;
        return liste.get(0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table des symboles {\n");
        for (List<Symbole> liste : this.values()) {
            for (Symbole s : liste) {
                sb.append("  ").append(s.afficherSymbole()).append("\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    // Ajout d’un symbole dans la table des symboles.
    // Si aucun symbole de ce nom n’existe, on crée une nouvelle liste.
    // Sinon, on vérifie qu’il n’existe pas déjà (via equals) avant de l’ajouter.
    public void ajouterSymbole(Symbole s) {
        // Récupération de la liste des symboles ayant le même nom
        List<Symbole> liste = this.get(s.getNom());
        // Aucun symbole avec ce nom : on crée une nouvelle entrée
        if (liste == null) {
            liste = new java.util.ArrayList<>();
            liste.add(s);
            this.put(s.getNom(), liste);
        }
        // Des symboles existent déjà : on vérifie les doublons
        else {
            // Vérifie si le symbole existe déjà dans la liste
            boolean exists = false;
            for (Symbole sym : liste) {
                if (sym.equals(s)) {
                    exists = true;
                    break;
                }
            }
            // Ajout uniquement s’il n’est pas déjà présent
            if (!exists) {
                liste.add(s);
            }
        }
    }
}