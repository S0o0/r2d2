package fr.ul.miashs.compil.traduction;

import fr.ul.miashs.compil.arbre.*;
// Test lié au début de l'étape 1 qui est maintenant obsolète
public class Main {
    public static void main(String[] args) {
        // créer la tds

        //on crée les noeuds
        Affectation aff = new Affectation();
        Idf x = new Idf("x");
        Plus plus = new Plus();
        Multiplication mul = new Multiplication();
        Idf a = new Idf("a");
        Const c2 = new Const(2);
        Division div = new Division();
        Moins moins = new Moins();
        Idf b = new Idf("b");
        Const c5 = new Const(5);
        Const c3 = new Const(3);
        //on relie les noeuds
        aff.setFilsGauche(x);
        aff.setFilsDroit(plus);
        plus.setFilsGauche(mul);
        plus.setFilsDroit(div);
        mul.setFilsGauche(a);
        mul.setFilsDroit(c2);
        div.setFilsGauche(moins);
        div.setFilsDroit(c3);
        moins.setFilsGauche(a);
        moins.setFilsDroit(c5);
        //afficher
        System.out.println("Arbre source :");
        TxtAfficheur.afficher(aff);
        System.out.println("\nCode généré :");
        fr.ul.miashs.compil.traduction.Generateur gen = new fr.ul.miashs.compil.traduction.Generateur();
        String code = gen.genererAffectation(aff);
        System.out.println(code);
    }
}
