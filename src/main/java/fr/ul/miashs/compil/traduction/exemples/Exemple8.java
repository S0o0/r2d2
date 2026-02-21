package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;
import fr.ul.miashs.compil.traduction.Generateur;

import java.awt.*;

public class Exemple8 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);
        //main
        Affectation affectation = new Affectation();
        main.ajouterUnFils(affectation);
        TantQue tq = new TantQue();
        main.ajouterUnFils(tq);
        //affectation
        Idf i = new Idf(0);
        affectation.ajouterUnFils(i);
        Const constante1 = new Const(0);
        affectation.ajouterUnFils(constante1);
        //tantque
        Inferieur inferieur = new Inferieur();
        tq.ajouterUnFils(inferieur);
        Bloc bloc = new Bloc();
        tq.ajouterUnFils(bloc);
        //inferieur
        inferieur.ajouterUnFils(i);
        Const constante2 = new Const(6);
        inferieur.ajouterUnFils(constante2);
        //bloc
        Ecrire ecrire = new Ecrire();
        bloc.ajouterUnFils(ecrire);
        Affectation affectation2 = new Affectation();
        bloc.ajouterUnFils(affectation2);
        //ecrire
        ecrire.ajouterUnFils(i);
        //affectation
        affectation2.ajouterUnFils(i);
        Plus plus = new Plus();
        affectation2.ajouterUnFils(plus);
        //plus
        plus.ajouterUnFils(i);
        Const constante3 = new Const(1);
        plus.ajouterUnFils(constante3);

        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("i","int","global",0,0,0);
        table.put(s1.getNom(), s1);
        table.put(s2.getNom(), s2);


        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        main.setValeur(s1);
        i.setValeur(s2);

        //afficher de deux manières
        TxtAfficheur.afficher(prog);
        GuiAfficheur.afficher(prog);

        Generateur generateur = new Generateur();
        System.out.println((generateur.genererProgramme(prog,table)));
    }
}
