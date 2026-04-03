package fr.ul.miashs.compil.tds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table extends HashMap<String, List<Symbole>>{

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

    public void ajouterSymbole(Symbole s) {
        List<Symbole> liste = this.get(s.getNom());
        if (liste == null) {
            liste = new java.util.ArrayList<>();
            liste.add(s);
            this.put(s.getNom(), liste);
        } else {
            boolean exists = false;
            for (Symbole sym : liste) {
                if (sym.equals(s)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                liste.add(s);
            }
        }
    }
}