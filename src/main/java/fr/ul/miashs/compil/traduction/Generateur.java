/**
 * Exercice d'introduction à la génération de code Beta
 * @author Azim Roussanaly
 * Created at 25 févr. 2026
 */
package fr.ul.miashs.compil.traduction;
import fr.ul.miashs.compil.arbre.*;
import fr.ul.miashs.compil.tds.Symbole;
import fr.ul.miashs.compil.tds.Table;
import java.util.concurrent.locks.Condition;

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
        Idf var = (Idf) aff.getFilsGauche();
        code.append("\tST(R0, " + var.getValeur() + ")\n");
        return code.toString();
        //👆adapter en fonction des variables globales, paramètres et locales
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
                code.append("\tCMOVE(R0, " + c.getValeur() + ")\n");
                code.append("\tPUSH(R0)\n");
                break;
            case IDF:
                Idf i = (Idf) expr;
                Symbole s = (Symbole) i.getValeur();
                int offset;
                switch (s.getCategorie()){
                    case "global":
                        code.append("\tCMOVE(R0, " + s.getValeur() + ")\n");
                        code.append("\tPUSH(R0)\n");
                        break;
                    case "param":
                        offset = 1 + s.getNb_param() + s.getRang();
                        code.append("GETFRAME("+offset * -4+",R0)");
                        code.append("\tPUSH(R0)\n");
                        break;
                    case "locale":
                        offset = -1 - s.getRang();
                        code.append("\tGETFRAME(" + (offset*4) + ",R0)\n");
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
                Noeud gaucheD = div.getFilsGauche();
                Noeud droitD = div.getFilsDroit();
                code.append(genererExpression(gaucheD));
                code.append(genererExpression(droitD));
                code.append("\tPOP(R2)\n");
                code.append("\tPOP(R1)\n");
                code.append("\tDIV(R1, R2, R3)\n");
                code.append("\tPUSH(R3)\n");
                break;
            case LIRE:
                //
            case APPEL:
                Appel ap = (Appel) expr;
                code.append(genererAppel(ap));
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
        code.append(genererData(tds));
        code.append("\tdebut:\n");
        code.append("\tCALL("+ programme.getLabel()+")\n");
        code.append(("\tHALT()\n"));

        for (Noeud fils : programme.getFils()) {
            Fonction f = (Fonction) fils;
            code.append(genererFonction(f));
        }
        code.append("\tpile\n");

        return code.toString();
    }

    /**
     * Générer le code pour les data
     * @param tds : table des symboles
     * @return code généré
     */
    public String genererData(Table tds){
        StringBuffer code = new StringBuffer();
        for (Symbole s : tds.values()){
            if ("global".equals(s.getCategorie())){
                code.append("\t"+s.getNom()+": LONG("+s.getValeur()+")\n");
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
        code.append("\t"+fonction.getLabel()+" :\n");
        code.append("\tPUSH(LP)\n");
        code.append("\tPUSH(BP)\n");
        code.append("\tMOVE(SP,BP)\n");
        // On parse en symbole pour avoir accès à nb_var_loc
        code.append("\tALLOCATE(+"+((Symbole)fonction.getValeur()).getNbVarLoc()+")\n");
        for  (Noeud fils : fonction.getFils()) {
            code.append(genererInstruction(fils));
        }
        code.append("\tret_"+fonction.getValeur()+"\n");
        // idem
        code.append("\tDEALLOCATE(+"+((Symbole)fonction.getValeur()).getNbVarLoc()+")\n");
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
        || instruction instanceof Si ||instruction instanceof TantQue){
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
        code.append("\tPOP(R0):\n");
        code.append("\tWRINT()\n");
        return code.toString();
    }


    public String genererAppel(Appel a){
        StringBuffer code = new StringBuffer();
        if (a.getValeur() != null){
            code.append("\tALLOCATE(1)\n");
        }
        for (Noeud fils : a.getFils()) {
            code.append(genererExpression(fils));
        }
        code.append("\tCALL(+"+a.getLabel()+")\n"); //nom de la fonction
        code.append("\tDEALLOCATE("+((Symbole)a.getValeur()).getNb_param()+")\n");
        return code.toString();
    }

    public String genererRetour(Retour retour){
        StringBuffer code = new StringBuffer();
        int offset;
        code.append(genererExpression(retour.getLeFils()));
        offset = 2 + ((Symbole)retour.getValeur()).getNb_param(); // BP
        code.append("\tPOP(R0)\n");
        code.append("\tPUTFRAME(R0,"+offset * 4+")\n");
        code.append("\tBR(ret_"+retour.getValeur()+")\n");
        return code.toString();
    }

    public String genererSi(Si si){
        StringBuffer code = new StringBuffer();
        code.append("\tSI"+si.getValeur()+" :\n");
        // On parse la condition car getCondition retourne un Noeud
        code.append(genererCondition((Condition)si.getCondition()));
        code.append("\tPOP(R0)\n");
        code.append("\tBF(R0,SINON_"+si.getValeur()+")\n");
        code.append(genererBloc(si.getBlocAlors()));
        code.append("\tBR(FSI_"+si.getValeur()+")\n");
        code.append("\tSINON_"+si.getValeur()+" :\n");
        code.append(genererBloc(si.getBlocSinon()));
        code.append("FSI_"+si.getValeur()+" :\n");
        return code.toString();
    }

    public String genererBloc (Bloc bloc){
        StringBuffer code = new StringBuffer();
        for (Noeud fils : bloc.getFils()) {
            code.append(genererInstruction(fils));
        }
        return code.toString();
    }

    public String genererCondition(Condition condition){
        StringBuffer code = new StringBuffer();
        // 1) cas où supérieur
        if (condition instanceof Superieur){
            code.append(genererExpression(((Superieur) condition).getFilsGauche()));
            code.append(genererExpression(((Superieur) condition).getFilsDroit()));
            code.append("\tPOP(R1)\n");
            code.append("\tPOP(R2)\n");
            code.append("\tCMPLT(R2,R1,R3)\n");
            code.append("\tPUSH(R3)\n");
        }
        // 2) cas où inférieur
        if (condition instanceof Inferieur) {
//
        }
        // 3) cas où égal

        // 4) cas où différent

        // 5) cas où inférieur ou égal

        // 6) cas où supérieur ou égal
        return  code.toString();
    }

    public String genererTq (TantQue tq){
        StringBuffer code = new StringBuffer();
        code.append("\tTQ_"+tq.getValeur()+" :\n");
        code.append(genererCondition((Condition)tq.getCondition()));
        code.append("\tPOP(R1)\n");
        code.append("\tBF(FTQ_"+tq.getValeur()+")\n");
        code.append(genererBloc(tq.getBloc()));
        code.append("\tBR(TQ_"+tq.getValeur() + ")\n");
        code.append("\tFTQ_"+tq.getValeur() + " :\n");

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