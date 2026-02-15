package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;

public class Exemple4 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);
        Affectation affectation = new Affectation();
        main.ajouterUnFils(affectation);
        Ecrire ecrire = new Ecrire();
        main.ajouterUnFils(ecrire);
        //affectation
        Idf x = new Idf(0);
        affectation.ajouterUnFils(x);
        Plus plus = new Plus();
        affectation.ajouterUnFils(plus);
        // plus
        Multiplication mul = new Multiplication();
        plus.ajouterUnFils(mul);
        //mul
        Lire lire = new Lire();
        mul.ajouterUnFils(lire);
        Const constante1 = new Const(2);
        mul.ajouterUnFils(constante1);
        Division div = new Division();
        mul.ajouterUnFils(div);
        //div
        Moins moins = new Moins();
        div.ajouterUnFils(moins);
        Const constante2 = new Const(3);
        div.ajouterUnFils(constante2);
        //moins
        Lire lire2 = new Lire();
        moins.ajouterUnFils(lire);
        Const constante3 = new Const(5);
        moins.ajouterUnFils(constante3);
        //ecrire
        ecrire.ajouterUnFils(x);


        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("res","int","global",0,0,0);
        table.put(s1.getNom(), s1);
        table.put(s2.getNom(), s2);

        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        main.setValeur(s1);
        x.setValeur(s2);

        //afficher de deux manières
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);
    }
}
