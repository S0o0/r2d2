package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;
import fr.ul.miashs.compil.traduction.Generateur;

public class Exemple3 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);
        //affectation
        Affectation affectation = new Affectation();
        main.ajouterUnFils(affectation);
        Idf x = new Idf(0);
        affectation.setFilsGauche(x);
        Plus plus = new Plus();
        affectation.setFilsDroit(plus);
        // plus
        Multiplication mul = new Multiplication();
        plus.setFilsGauche(mul);

        Division div = new Division();
        plus.setFilsDroit(div);

        // multiplication : a * 2
        Idf a = new Idf(100);
        mul.setFilsGauche(a);

        Const constante1 = new Const(2);
        mul.setFilsDroit(constante1);

        // division : (b-5) / 3
        Moins moins = new Moins();
        div.setFilsGauche(moins);

        Const constante2 = new Const(3);
        div.setFilsDroit(constante2);

        // moins : b - 5
        Idf b = new Idf(170);
        moins.setFilsGauche(b);

        Const constante3 = new Const(5);
        moins.setFilsDroit(constante3);


        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("x","int","global",0,0,0);
        Symbole s3 = new Symbole("a","int","global",100,0,0);
        Symbole s4 = new Symbole("b","int","global",170,0,0);
        table.ajouterSymbole(s1);
        table.ajouterSymbole(s2);
        table.ajouterSymbole(s3);
        table.ajouterSymbole(s4);
        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        main.setValeur(s1);
        x.setValeur(s2);
        a.setValeur(s3);
        b.setValeur(s4);

        //Affichage de l'arbre
        TxtAfficheur.afficher(prog);

        //Affichage du code assembleur
        Generateur generateur = new Generateur();
        System.out.println((generateur.genererProgramme(prog,table)));

        //Affichage de la table des symboles
        System.out.println(table);
    }
}
