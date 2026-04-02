package fr.ul.miashs.compil.tds;

import java.util.HashMap;
import java.util.List;

public class Table extends HashMap<String, List<Symbole>>{

    public List<Symbole> rechercherSymbole(String nom) {
        if (nom == null) return null;
        return this.get(nom);
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

    public void ajouterSymbole(Symbole s) {
        if (!this.containsKey(s.getNom())) {
            List<Symbole> liste = new java.util.ArrayList<>();
            liste.add(s);
            this.put(s.getNom(), liste);
        } else {
            List<Symbole> liste = this.get(s.getNom());
            liste.add(s);
        }
    }
}