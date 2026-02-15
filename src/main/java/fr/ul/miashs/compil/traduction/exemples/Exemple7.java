package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;

import java.awt.*;

public class Exemple7 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);
        Si si = new Si();
        main.ajouterUnFils(si);
        //si
        Superieur superieur = new Superieur();
        si.ajouterUnFils(superieur);
        Bloc bloc1 = new Bloc();
        si.ajouterUnFils(bloc1);
        Bloc bloc2 = new Bloc();
        si.ajouterUnFils(bloc2);
        //superieur
        Idf a = new Idf(1);
        superieur.ajouterUnFils(a);
        Idf b = new Idf(2);
        superieur.ajouterUnFils(b);
        //bloc1
        Affectation affectation1 = new Affectation();
        bloc1.ajouterUnFils(affectation1);
        //affectation1
        Idf x = new Idf(0);
        affectation1.ajouterUnFils(x);
        Const constante1 = new Const(1000);
        affectation1.ajouterUnFils(constante1);
        //bloc2
        Affectation affectation2 = new Affectation();
        bloc2.ajouterUnFils(affectation2);
        //affectation2
        affectation2.ajouterUnFils(x);
        Const constante2 = new Const(2000);
        affectation2.ajouterUnFils(constante2);

        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("a","int","global",1,0,0);
        Symbole s3 = new Symbole("b","int","global",2,0,0);
        Symbole s4 = new Symbole("x","int","global",0,0,0);
        table.put(s1.getNom(), s1);
        table.put(s2.getNom(), s2);
        table.put(s3.getNom(), s3);
        table.put(s4.getNom(), s4);


        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        main.setValeur(s1);
        a.setValeur(s2);
        b.setValeur(s3);
        x.setValeur(s4);

        //afficher de deux manières
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);
    }
}
