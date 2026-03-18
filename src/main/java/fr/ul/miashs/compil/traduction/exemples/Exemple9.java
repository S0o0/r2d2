package fr.ul.miashs.compil.traduction.exemples;

import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.*;
import fr.ul.miashs.compil.traduction.Generateur;

import java.awt.*;

public class Exemple9 {
    public static void main(String[] args) {
        // Création de l'arbre
        Prog prog = new Prog();
        Fonction f = new Fonction("f");
        prog.ajouterUnFils(f);
        Fonction main = new Fonction("main");
        prog.ajouterUnFils(main);
        //fonction f
        Si si = new Si();
        f.ajouterUnFils(si);
        Retour retour = new Retour("retour"); // jsp si c'est retour qu'il faut mettre
        f.ajouterUnFils(retour);
        //si
        InferieurEgal inferieurEgal = new InferieurEgal();
        si.setCondition(inferieurEgal);
        Bloc bloc = new Bloc();
        si.setBlocAlors(bloc);
        //inferieuregal
        Idf a = new Idf(0);
        inferieurEgal.setFilsGauche(a);
        Const constante = new Const(0);
        inferieurEgal.setFilsDroit(constante);
        //bloc
        Retour retour2 = new Retour("retour2"); // pareil qu'au dessus
        bloc.ajouterUnFils(retour2);
        //retour2
        retour2.setLeFils(constante);
        //retour
        Plus plus = new Plus();
        retour.setLeFils(plus);
        //plus
        plus.setFilsGauche(a);
        Appel appel = new Appel("f");
        plus.setFilsDroit(appel);
        //appel
        Moins moins = new Moins();
        appel.ajouterUnFils(moins);
        //moins
        moins.setFilsGauche(a);
        Const constante2 = new Const(1);
        moins.setFilsDroit(constante2);
        //main
        Ecrire ecrire = new Ecrire();
        main.ajouterUnFils(ecrire);
        //ecrire
        Appel appel2 = new Appel("f"); // je sais pas si je dois utiliser l'appel f d'avant ou un nouveau pck la c'est pour afficher 6 donc jsp
        ecrire.setLeFils(appel2);
        //appel2
        Const constante3 = new Const(6);
        appel2.ajouterUnFils(constante3);

        //Affectation du symbole
        Table table = new Table();
        Symbole s1 = new Symbole("main","void","fonction",0,0,0);
        Symbole s2 = new Symbole("f","int","fonction",0,1,0);
        Symbole s3 = new Symbole("a","int","param",0,s2);
        table.put(s1.getNom(), s1);
        table.put(s2.getNom(), s2);
        table.put(s3.getNom(), s3);


        //Faire pointer les noeuds aux symboles (avec le setter de NoeudObj)
        main.setValeur(s1);
        f.setValeur(s2);
        a.setValeur(s3);
        retour.setValeur(s2);
        retour2.setValeur(s2);

        //Affichage de l'arbre
        TxtAfficheur.afficher(prog);

        //Affichage du code assembleur
        Generateur generateur = new Generateur();
        System.out.println((generateur.genererProgramme(prog,table)));

        //Affichage de la table des symboles
        System.out.println(table);
    }
}
