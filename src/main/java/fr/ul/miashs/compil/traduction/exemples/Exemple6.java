package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;
import fr.ul.miashs.compil.traduction.Generateur;

import java.awt.*;
import java.util.ArrayList;

public class Exemple6 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction f = new Fonction("f");
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(f);
        prog.ajouterUnFils(main);
        Affectation affectation = new Affectation();
        f.ajouterUnFils(affectation);
        Retour retour = new Retour("retour");
        f.ajouterUnFils(retour);
        //affectation
        Idf res = new Idf("res");
        affectation.setFilsGauche(res);
        Plus plus = new Plus();
        affectation.setFilsDroit(plus);
        //plus
        Multiplication mul = new Multiplication();
        plus.setFilsGauche(mul);
        Division div = new Division();
        plus.setFilsDroit(div);
        //mul
        Idf aparam = new Idf(null);
        mul.setFilsGauche(aparam);
        Const constante1 = new Const(2);
        mul.setFilsDroit(constante1);
        //div
        Moins moins = new Moins();
        div.setFilsGauche(moins);
        Const constante2 = new Const(3);
        div.setFilsDroit(constante2);
        //moins
        Idf b = new Idf(null);
        moins.setFilsGauche(b);
        Const constante3 = new Const(5);
        moins.setFilsDroit(constante3);
        //retour
        retour.setLeFils(res);
        //main
        Ecrire ecrire = new Ecrire();
        main.ajouterUnFils(ecrire);
        //ecrire
        Appel appel = new Appel("f");
        ecrire.ajouterUnFils(appel);
        //appel
        Idf a2 = new Idf("a");
        appel.ajouterUnFils(a2);
        Idf c = new Idf("c");
        appel.ajouterUnFils(c);

        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("f","int","fonction",0,2,1);
        Symbole s3 = new Symbole("a","int","global",100,0,0);
        Symbole s4 = new Symbole("c","int","global",170,0,0);
        Symbole s5 = new Symbole("aparam","int","param",0, s2);
        Symbole s6 = new Symbole("b","int","param",1, s2);
        Symbole s7 = new Symbole("res","int","local",0, s2);
        table.put(s1.getNom(), s1);
        table.put(s2.getNom(), s2);
        table.put(s3.getNom(), s3);
        table.put(s4.getNom(), s4);
        table.put(s5.getNom(), s5);
        table.put(s6.getNom(), s6);
        table.put(s7.getNom(), s7);

        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        main.setValeur(s1);
        f.setValeur(s2);
        a2.setValeur(s3);
        c.setValeur(s4);
        aparam.setValeur(s5);
        b.setValeur(s6);
        res.setValeur(s7);

        //Affichage de l'arbre
        TxtAfficheur.afficher(prog);

        //Affichage du code assembleur
        Generateur generateur = new Generateur();
        System.out.println((generateur.genererProgramme(prog,table)));

        //Affichage de la table des symboles
        System.out.println(table);
    }
}
