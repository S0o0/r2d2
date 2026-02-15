package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;

public class Exemple2 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);

        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("i","int","global",10,0,1);
        Symbole s3 = new Symbole("j","int","global",20,0,2);
        Symbole s4 = new Symbole("k","int","global",0,0,3);
        Symbole s5 = new Symbole("l","int","global",0,0,4);
        table.put(s1.getNom(), s1);
        table.put(s2.getNom(), s2);
        table.put(s3.getNom(), s3);
        table.put(s4.getNom(), s4);
        table.put(s5.getNom(), s5);
        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        main.setValeur(s1);

        //afficher de deux manières
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);
    }
}
