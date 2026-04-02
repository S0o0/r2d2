package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;
import fr.ul.miashs.compil.traduction.Generateur;

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
        affectation.setFilsGauche(x);
        Plus plus = new Plus();
        affectation.setFilsDroit(plus);
        // plus
        Multiplication mul = new Multiplication();
        plus.setFilsGauche(mul);
        //mul
        Lire lire = new Lire();
        mul.setFilsGauche(lire);
        Const constante1 = new Const(2);
        mul.setFilsDroit(constante1);
        Division div = new Division();
        plus.setFilsDroit(div);
        //div
        Moins moins = new Moins();
        div.setFilsGauche(moins);
        Const constante2 = new Const(3);
        div.setFilsDroit(constante2);
        //moins
        Lire lire2 = new Lire();
        moins.setFilsGauche(lire2);
        Const constante3 = new Const(5);
        moins.setFilsDroit(constante3);
        //ecrire
        ecrire.ajouterUnFils(x);


        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("res","int","global",0,0,0);
        table.ajouterSymbole(s1);
        table.ajouterSymbole(s2);

        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        main.setValeur(s1);
        x.setValeur(s2);

        //Affichage de l'arbre
        TxtAfficheur.afficher(prog);

        //Affichage du code assembleur
        Generateur generateur = new Generateur();
        System.out.println((generateur.genererProgramme(prog,table)));

        //Affichage de la table des symboles
        System.out.println(table);
    }
}
