package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;

public class Exemple1 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);

        //Affectation du symbole
        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        Table table = new Table();
        Symbole s = new Symbole("main","void","fonction",0,0,0);
        table.put(s.getNom(),s);
        main.setValeur(s);

        //afficher de deux manières
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);
    }
}
