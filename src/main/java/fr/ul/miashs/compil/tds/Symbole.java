package fr.ul.miashs.compil.tds;

public class Symbole {
    String nom;
    String type;
    String categorie;
    int valeur;
    int nb_param;
    int nb_var_loc;

    public Symbole(String nom, String type, String categorie, int valeur, int nb_param, int nb_var_loc){
        this.nom = nom;
        this.type = type;
        this.categorie = categorie;
        this.valeur = valeur;
        this.nb_param = nb_param;
        this.nb_var_loc = nb_var_loc;
    }

    public String getNom(){
        return nom;
    }
    public String getType(){
        return type;
    }
    public String getCategorie(){
        return categorie;
    }
    public int getValeur(){
        return valeur;
    }
    public int getNb_param(){return nb_param;}
    public int getNbVarLoc(){return nb_var_loc;}
}
