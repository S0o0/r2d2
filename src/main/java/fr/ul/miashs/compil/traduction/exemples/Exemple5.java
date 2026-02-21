package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;
import fr.ul.miashs.compil.traduction.Generateur;

public class Exemple5 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);
        Ecrire ecrire = new Ecrire();
        main.ajouterUnFils(ecrire);
        //ecrire
        Plus plus = new Plus();
        ecrire.ajouterUnFils(plus);
        //plus
        Multiplication mul = new Multiplication();
        plus.ajouterUnFils(mul);
        Division div = new Division();
        plus.ajouterUnFils(div);
        //mul
        Idf a = new Idf(100);
        mul.ajouterUnFils(a);
        Const constante1 = new Const(2);
        mul.ajouterUnFils(constante1);
        //div
        Moins moins = new Moins();
        div.ajouterUnFils(moins);
        Const constante2 = new Const(3);
        div.ajouterUnFils(constante2);
        //moins
        Idf b = new Idf(170);
        moins.ajouterUnFils(b);
        Const constante3 = new Const(5);
        moins.ajouterUnFils(constante3);

        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("a","int","global",100,0,0);
        Symbole s3 = new Symbole("b","int","global",170,0,0);
        table.put(s1.getNom(), s1);
        table.put(s2.getNom(), s2);
        table.put(s3.getNom(), s3);

        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        main.setValeur(s1);
        a.setValeur(s2);
        b.setValeur(s3);

        //afficher de deux manières
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);

        Generateur generateur = new Generateur();
        System.out.println((generateur.genererProgramme(prog,table)));
    }
}
