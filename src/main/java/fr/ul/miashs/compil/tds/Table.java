package fr.ul.miashs.compil.tds;

import java.util.HashMap;

public class Table extends HashMap<String, Symbole>{
    //main
    //a
    //b
    //x

    // S'assurer qu'on puisse ajouter, supprimer et rechercher des symboles

    public Symbole rechercherSymbole(String nom) {
        if (nom != null) {
            return this.get(nom);
        }
        return null;
    }
}
