package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;
import fr.ul.miashs.compil.traduction.Generateur;

public class Exemple2 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);

        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("i","int","global",10,0,0);
        Symbole s3 = new Symbole("j","int","global",20,0,0);
        Symbole s4 = new Symbole("k","int","global",0,0,0);
        Symbole s5 = new Symbole("l","int","global",0,0,0);
        table.ajouterSymbole(s1);
        table.ajouterSymbole(s2);
        table.ajouterSymbole(s3);
        table.ajouterSymbole(s4);
        table.ajouterSymbole(s5);
        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        main.setValeur(s1);

        //Affichage de l'arbre
        TxtAfficheur.afficher(prog);

        //Affichage du code assembleur
        Generateur generateur = new Generateur();
        System.out.println((generateur.genererProgramme(prog,table)));

        //Affichage de la table des symboles
        System.out.println(table);
    }
}
