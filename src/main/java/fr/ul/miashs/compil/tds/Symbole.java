package fr.ul.miashs.compil.tds;

public class Symbole {
    String nom;
    String type;
    String categorie;
    int valeur;
    int nb_param;
    int nb_var_loc;
    int rang;
    Symbole scope;

    public Symbole(String nom, String type, String categorie, int valeur, int nb_param, int nb_var_loc){
        this.nom = nom;
        this.type = type;
        this.categorie = categorie;
        this.valeur = valeur;
        this.nb_param = nb_param;
        this.nb_var_loc = nb_var_loc;
    }
    // constructeur pour les exemples nécessitant un scope
    public Symbole(String nom, String type, String categorie, int rang, Symbole scope){
        this.nom = nom;
        this.type = type;
        this.categorie = categorie;
        this.rang = rang;
        this.scope = scope;
    }
    // constructeur pour les exemples nécessitant un scope, nb_param et nb_var_loc
    public Symbole(String nom, String type, String categorie, int valeur, int nb_param, int nb_var_loc, int rang, Symbole scope){
        this.nom = nom;
        this.type = type;
        this.categorie = categorie;
        this.valeur = valeur;
        this.nb_param = nb_param;
        this.nb_var_loc = nb_var_loc;
        this.rang = rang;
        this.scope = scope;
    }
    //Getters
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
    public int getRang(){return rang;}
    public Symbole getScope(){return scope;}

    // Renommage de toString afin qu'on renvoie le nom du symbole en le printant
    @Override
    public String toString(){
        return nom;
    }

    // Affichage du symbole prévue dans la table des symboles
    public String afficherSymbole() {
        StringBuilder sb = new StringBuilder();
        sb.append("nom = ").append(nom);
        sb.append(", type = ").append(type);
        sb.append(", cat = ").append(categorie);

        // Affichage conditionnel
        if (valeur != 0) {
            sb.append(", val = ").append(valeur);
        }
        if (nb_param != 0) {
            sb.append(", nb_param = ").append(nb_param);
        }
        if (nb_var_loc != 0) {
            sb.append(", nb_var_loc = ").append(nb_var_loc);
        }
        sb.append(", rang= ").append(rang);
        if (scope != null) {
            sb.append(", scope = ").append(scope);
        }
        return sb.toString();
    }

    // Un symbole est égal à un autre s'il le même nom, type et catégorie.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Symbole s = (Symbole) obj;
        return nom.equals(s.nom) &&
                type.equals(s.type) &&
                categorie.equals(s.categorie);
    }
}