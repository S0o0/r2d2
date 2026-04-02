/**
 * Exercice d'introduction à la génération de code Beta
 * @author Azim Roussanaly
 * Created at 25 févr. 2026
 */
package fr.ul.miashs.compil.traduction;
import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.Symbole;
import fr.ul.miashs.compil.tds.Table;

import java.util.List;

/**
 * Générateur de code pour un arbre d'affectation
 */
public class Generateur {
    /**
     * Générer le code pour une affectation
     * @param aff : noeud d'affectation
     * @return code généré
     */
    public String genererAffectation(Affectation aff) {
        StringBuffer code = new StringBuffer();
        code.append(genererExpression(aff.getFilsDroit()));
        code.append("\tPOP(R0)\n");
        Idf i = (Idf) aff.getFilsGauche();
        Symbole s = (Symbole) i.getValeur();
        int offset;
        // Suivant les types (global, param et local)
        switch (s.getCategorie()) {
            case "global":
                code.append("\tST(R0, " + s.getNom() + ")\n");
                break;
            case "param":
                offset = -(2 + s.getScope().getNb_param() - s.getRang()) * 4;
                code.append("\tPUTFRAME("+offset+",R0)\n");
                break;
            case "local":
                offset = -(s.getRang() + 1) * 4;
                code.append("\tPUTFRAME(" + offset + ",R0)\n");
                break;
        }
        return code.toString();
    }
    /**
     * Générer le code pour une expression
     * @param expr : noeud d'expression
     * @return code généré
     */
    public String genererExpression(Noeud expr) {
        StringBuffer code = new StringBuffer();
        switch (expr.getCat()){
            case CONST:
                Const c = (Const) expr;
                code.append("\tCMOVE(" + c.getValeur() + ", R0)\n");
                code.append("\tPUSH(R0)\n");
                break;
            case IDF:
                Idf i = (Idf) expr;
                Symbole s = (Symbole) i.getValeur();
                int offset;
                switch (s.getCategorie()){
                    case "global":
                        code.append("\tLD(" + s.getNom() + ", R0)\n");
                        code.append("\tPUSH(R0)\n");
                        break;
                    case "param":
                        offset = -(2 + s.getScope().getNb_param() - s.getRang()) * 4;
                        code.append("\tGETFRAME("+offset+",R0)\n");
                        code.append("\tPUSH(R0)\n");
                        break;
                    case "local":
                        offset =  -(s.getRang() + 1) * 4;
                        code.append("\tGETFRAME(" + offset + ",R0)\n");
                        code.append("\tPUSH(R0)\n");
                        break;
                }
                
                break;
            case PLUS:
                Plus p = (Plus) expr;
                Noeud gaucheP = p.getFilsGauche();
                Noeud droitP = p.getFilsDroit();
                code.append(genererExpression(gaucheP));
                code.append(genererExpression(droitP));
                code.append("\tPOP(R2)\n");
                code.append("\tPOP(R1)\n");
                code.append("\tADD(R1, R2, R3)\n");
                code.append("\tPUSH(R3)\n");
                break;
            case MOINS:
                Moins m = (Moins) expr;
                Noeud gaucheM = m.getFilsGauche();
                Noeud droitM = m.getFilsDroit();
                code.append(genererExpression(gaucheM));
                code.append(genererExpression(droitM));
                code.append("\tPOP(R2)\n");
                code.append("\tPOP(R1)\n");
                code.append("\tSUB(R1, R2, R3)\n");
                code.append("\tPUSH(R3)\n");
                break;
            case MUL:
                Multiplication mul = (Multiplication) expr;
                Noeud gaucheMul = mul.getFilsGauche();
                Noeud droitMul = mul.getFilsDroit();
                code.append(genererExpression(gaucheMul));
                code.append(genererExpression(droitMul));
                code.append("\tPOP(R2)\n");
                code.append("\tPOP(R1)\n");
                code.append("\tMUL(R1, R2, R3)\n");
                code.append("\tPUSH(R3)\n");
                break;
            case DIV:
                Division div = (Division) expr;
                code.append(genererExpression(div.getFilsGauche()));
                code.append(genererExpression(div.getFilsDroit()));
                code.append("\tPOP(R2)\n");
                code.append("\tPOP(R1)\n");
                code.append("\tDIV(R1, R2, R3)\n");
                code.append("\tPUSH(R3)\n");
                break;
            case LIRE:
                Lire lire = (Lire) expr;
                code.append("\tRDINT()\n");
                code.append("\tPUSH(R0)\n");
                break;
            case APPEL:
                Appel ap = (Appel) expr;
                code.append(genererAppel(ap));
                break;
            default:
                break;
        }
        return code.toString();
    }
    /**
     * Générer le code pour un programme
     * @param programme : noeud de programme
     * @param tds : table des symboles
     * @return code généré
     */
    public String genererProgramme(Prog programme, Table tds){
        StringBuffer code = new StringBuffer();
        code.append(".include beta.uasm\n");
        code.append(".include intio.uasm\n");
        code.append(".options tty\n");
        code.append("\tCMOVE(pile, SP)\n");
        code.append("\tBR(debut)\n");
        code.append(genererData(tds));
        code.append("debut:\n");
        // retourne la dernière fonction soit main quand il y en a plusieurs
        Fonction fction = (Fonction) programme.getFils().get(programme.getFils().size()-1);
        code.append("\tCALL(" + fction.getValeur() + ")\n");
        code.append(("\tHALT()\n"));

        for (Noeud fils : programme.getFils()) {
            Fonction f = (Fonction) fils;
            code.append(genererFonction(f));
        }
        code.append("pile:\n");
        code.append("\tSTORAGE(1000)\n");
        return code.toString();
    }

    /**
     * Générer le code pour les data
     * @param tds : table des symboles
     * @return code généré
     */
    public String genererData(Table tds){
        StringBuffer code = new StringBuffer();
        for (List<Symbole> liste : tds.values()) {
            for (Symbole s : liste) {
                if ("global".equals(s.getCategorie())) {
                    code.append("\t" + s.getNom() + ": LONG(" + s.getValeur() + ")\n");
                }
            }
        }
        return code.toString();
    }

    /**
     * Générer le code pour une fonction
     * @param fonction : noeud de fonction
     * @return code généré
     */
    public String genererFonction(Fonction fonction){
        StringBuffer code = new StringBuffer();
        code.append(fonction.getValeur()+":\n");
        code.append("\tPUSH(LP)\n");
        code.append("\tPUSH(BP)\n");
        code.append("\tMOVE(SP,BP)\n");
        // On ex1 en symbole pour avoir accès à nb_var_loc
        code.append("\tALLOCATE("+((Symbole)fonction.getValeur()).getNbVarLoc()+")\n");
        for  (Noeud fils : fonction.getFils()) {
            code.append(genererInstruction(fils));
        }
        code.append("ret_"+fonction.getValeur()+":\n");
        // idem
        code.append("\tDEALLOCATE("+((Symbole)fonction.getValeur()).getNbVarLoc()+")\n");
        code.append("\tPOP(BP)\n");
        code.append("\tPOP(LP)\n");
        code.append("\tRTN()\n");
        return code.toString();
    }


    /**
     * Générer le code pour une instruction
     * @param instruction : noeud d'instruction
     * @return code généré
     */
    public String genererInstruction(Noeud instruction){
        StringBuffer code = new StringBuffer();
        if ( instruction instanceof Affectation || instruction instanceof Appel || instruction instanceof Ecrire
        || instruction instanceof Si ||instruction instanceof TantQue || instruction instanceof Retour){
            switch(instruction.getCat()){
                case AFF:
                    Affectation a = (Affectation) instruction;
                    code.append(genererAffectation(a));
                    break;
                case APPEL:
                    Appel ap = (Appel) instruction;
                    code.append(genererAppel(ap));
                    break;
                case ECR:
                    Ecrire e = (Ecrire) instruction;
                    code.append(genererEcriture(e));
                    break;
                case SI:
                    Si  si = (Si) instruction;
                    code.append(genererSi(si));
                    break;
                case TQ:
                    TantQue  tq = (TantQue) instruction;
                    code.append(genererTq(tq));
                    break;
                case RET:
                    Retour ret =  (Retour) instruction;
                    code.append(genererRetour(ret));
                    break;
            }

        }
        return code.toString();
    }


    public String genererEcriture(Ecrire e){
        StringBuffer code = new StringBuffer();;
        code.append(genererExpression(e.getLeFils()));
        code.append("\tPOP(R0)\n");
        code.append("\tWRINT()\n");
        return code.toString();
    }


    public String genererAppel(Appel a){
        StringBuffer code = new StringBuffer();
        boolean aValeurDeRetour = a.getValeur() != null
                && a.getValeur() instanceof Symbole
                && !"void".equals(((Symbole)a.getValeur()).getType());

        if (aValeurDeRetour){
            code.append("\tALLOCATE(1)\n");
        }
        for (Noeud fils : a.getFils()) {
            code.append(genererExpression(fils));
        }
        code.append("\tCALL("+a.getValeur()+")\n");
        code.append("\tDEALLOCATE("+a.getFils().size()+")\n");
        if (aValeurDeRetour){
            int nb_param = ((Symbole)a.getValeur()).getNb_param();
            int offsetRetour = -(nb_param + 1) * 4;
            code.append("\tGETFRAME(" + offsetRetour + ",R0)\n");
            code.append("\tDEALLOCATE(1)\n");
            code.append("\tPUSH(R0)\n");
        }
        return code.toString();
    }

    public String genererRetour(Retour retour){
        StringBuffer code = new StringBuffer();
        Symbole s = (Symbole) retour.getValeur();
        int nb_param = s.getNb_param();
        int offset = (2 + nb_param + 1) * 4;
        code.append(genererExpression(retour.getLeFils()));
        code.append("\tPOP(R0)\n");
        code.append("\tPUTFRAME(" + offset + ",R0)\n");
        code.append("\tBR(ret_" + retour.getValeur() + ")\n");
        return code.toString();
    }

    public String genererSi(Si si){
        StringBuffer code = new StringBuffer();
        code.append("si_"+si.getValeur()+" :\n");
        // On ex1 la condition car getCondition retourne un Noeud
        code.append(genererCondition(si.getCondition()));
        code.append("\tPOP(R0)\n");
        code.append("\tBF(R0,sinon_"+si.getValeur()+")\n");
        code.append(genererBloc(si.getBlocAlors()));
        code.append("\tBR(fsi_"+si.getValeur()+")\n");
        code.append("sinon_"+si.getValeur()+" :\n");
        code.append(genererBloc(si.getBlocSinon()));
        code.append("fsi_"+si.getValeur()+" :\n");
        return code.toString();
    }

    public String genererBloc (Bloc bloc){
        StringBuffer code = new StringBuffer();
        for (Noeud fils : bloc.getFils()) {
            code.append(genererInstruction(fils));
        }
        return code.toString();
    }

    public String genererCondition(Noeud condition){
        StringBuffer code = new StringBuffer();
        // 1) cas où supérieur
        if (condition instanceof Superieur){
            code.append(genererExpression(((Superieur) condition).getFilsGauche()));
            code.append(genererExpression(((Superieur) condition).getFilsDroit()));
            code.append("\tPOP(R1)\n");
            code.append("\tPOP(R2)\n");
            code.append("\tCMPLT(R1,R2,R3)\n");
            code.append("\tPUSH(R3)\n");
        }
        // 2) cas où inférieur
        if (condition instanceof Inferieur) {
            code.append(genererExpression(((Inferieur) condition).getFilsGauche()));
            code.append(genererExpression(((Inferieur) condition).getFilsDroit()));
            code.append("\tPOP(R1)\n");
            code.append("\tPOP(R2)\n");
            code.append("\tCMPLT(R2,R1,R3)\n");
            code.append("\tPUSH(R3)\n");
        }
        if (condition instanceof Egal){
            code.append(genererExpression(((Egal) condition).getFilsGauche()));
            code.append(genererExpression(((Egal) condition).getFilsDroit()));
            code.append("\tPOP(R1)\n");
            code.append("\tPOP(R2)\n");
            code.append("\tCMPEQ(R2,R1,R3)\n");
            code.append("\tPUSH(R3)\n");
        }
        if (condition instanceof Different){
            code.append(genererExpression(((Different) condition).getFilsGauche()));
            code.append(genererExpression(((Different) condition).getFilsDroit()));
            code.append("\tPOP(R1)\n");
            code.append("\tPOP(R2)\n");
            code.append("\tCMPEQ(R2,R1,R3)\n");
            code.append("\tCMPEQC(R3,0,R3)\n");
            code.append("\tPUSH(R3)\n");
        }
        if (condition instanceof InferieurEgal){
            code.append(genererExpression(((InferieurEgal) condition).getFilsGauche()));
            code.append(genererExpression(((InferieurEgal) condition).getFilsDroit()));
            code.append("\tPOP(R1)\n");
            code.append("\tPOP(R2)\n");
            code.append("\tCMPLE(R2,R1,R3)\n");
            code.append("\tPUSH(R3)\n");
        }
        if (condition instanceof SuperieurEgal){
            code.append(genererExpression(((SuperieurEgal) condition).getFilsGauche()));
            code.append(genererExpression(((SuperieurEgal) condition).getFilsDroit()));
            code.append("\tPOP(R1)\n");
            code.append("\tPOP(R2)\n");
            code.append("\tCMPLE(R1,R2,R3)\n");
            code.append("\tPUSH(R3)\n");
        }
        return  code.toString();
    }

    public String genererTq (TantQue tq){
        StringBuffer code = new StringBuffer();
        code.append("tq_"+tq.getValeur()+" :\n");
        code.append(genererCondition(tq.getCondition()));
        code.append("\tPOP(R1)\n");
        code.append("\tBF(R1, ftq_"+tq.getValeur()+")\n");
        code.append(genererBloc(tq.getBloc()));
        code.append("\tBR(tq_"+tq.getValeur() + ")\n");
        code.append("ftq_"+tq.getValeur() + " :\n");

        return code.toString();
    }
}


//Exemple 7 (en C)
//int a = 1;
//int b = 2;
//int x;
//
//void main(){
//    if (a > b){
//        x = 1000;
//    }else{
//        x = 2000;
//    }