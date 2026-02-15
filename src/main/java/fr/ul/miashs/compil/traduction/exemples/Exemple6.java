package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;

import java.awt.*;

public class Exemple6 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction f = new Fonction("f");
        prog.ajouterUnFils(f);
        //fonction f
        Affectation affectation = new Affectation();
        f.ajouterUnFils(affectation);
        Retour retour = new Retour("retour");
        f.ajouterUnFils(retour);
        //affectation
        Idf res = new Idf("res");
        affectation.ajouterUnFils(res);
        Plus plus = new Plus();
        affectation.ajouterUnFils(plus);
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
        Idf b = new Idf(170); // il a mis b dans le dessin mais dans la tds il met c donc je mets b c'est plus logique jpense
        moins.ajouterUnFils(b);
        Const constante3 = new Const(5);
        moins.ajouterUnFils(constante3);
        //retour
        retour.ajouterUnFils(res);
        //main
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);
        Ecrire ecrire = new Ecrire();
        main.ajouterUnFils(ecrire);
        //ecrire
        Appel appel = new Appel(2); // je sais pas faut mettre quoi en parametre, le nombre de parametre ? genre a et c
        ecrire.ajouterUnFils(appel);
        //appel
        Idf a2 = new Idf(0);
        appel.ajouterUnFils(a2);
        Idf c = new Idf(0);
        appel.ajouterUnFils(c);

        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("f","int","fonction",0,2,1);
        Symbole s3 = new Symbole("a","int","global",100,0,0);
        Symbole s4 = new Symbole("b","int","global",170,0,0);
        Symbole s5 = new Symbole("a","int","param",0,0,0);
        Symbole s6 = new Symbole("c","int","param",0,1,0);
        Symbole s7 = new Symbole("res","int","local",0,0,1);
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
        a.setValeur(s3);
        b.setValeur(s4);
        a2.setValeur(s5);
        c.setValeur(s6);
        res.setValeur(s7);

        //afficher de deux manières
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);
    }
}
