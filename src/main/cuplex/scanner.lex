package fr.ul.miashs.compil.parsing;
import java_cup.runtime.*;
//Section options et déclarations
%%
/* options */
%public
%cupsym Sym
%cup

%{
    /**
     * Fabrique un Symbol avec position (ligne, colonne)
     */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

//Définitions (macros)
CHIFFRE     = [0-9]
LETTRE      = [a-zA-Z_]
ENTIER      = {CHIFFRE}+
IDENT       = {LETTRE}({LETTRE}|{CHIFFRE})*
ESPACE      = [ \t\r\n]+

//États
%state COMMENTAIRE_BLOC

%%

//Espaces blancs
<YYINITIAL> {ESPACE}            { /* ignoré */ }

//Commentaires ligne
<YYINITIAL> "//"[^\n]*          { /* ignoré */ }

//Commentaires bloc
<YYINITIAL> "/*"                { yybegin(COMMENTAIRE_BLOC); }
<COMMENTAIRE_BLOC> "*/"         { yybegin(YYINITIAL); }
<COMMENTAIRE_BLOC> [^]          { /* ignoré */ }

//Mots-clés de types
<YYINITIAL> "int"               { return symbol(Sym.TYPE_INT);  }
<YYINITIAL> "void"              { return symbol(Sym.TYPE_VOID); }

//Mots-clés de contrôle
<YYINITIAL> "si"                { return symbol(Sym.SI);      }
<YYINITIAL> "sinon"             { return symbol(Sym.SINON);   }
<YYINITIAL> "tantque"           { return symbol(Sym.TANTQUE); }
<YYINITIAL> "retour"            { return symbol(Sym.RETOUR);  }

//Mots-clés E/S
<YYINITIAL> "ecrire"            { return symbol(Sym.ECRIRE); }
<YYINITIAL> "lire"              { return symbol(Sym.LIRE);   }

//Opérateurs arithmétiques
<YYINITIAL> "+"                 { return symbol(Sym.PLUS);  }
<YYINITIAL> "-"                 { return symbol(Sym.MOINS); }
<YYINITIAL> "*"                 { return symbol(Sym.MUL);   }
<YYINITIAL> "/"                 { return symbol(Sym.DIV);   }

//Opérateurs de comparaison (2 caractères avant 1 caractère)
<YYINITIAL> ">="                { return symbol(Sym.SUP_EG); }
<YYINITIAL> "<="                { return symbol(Sym.INF_EG); }
<YYINITIAL> "=="                { return symbol(Sym.EGAL);   }
<YYINITIAL> "!="                { return symbol(Sym.DIFF);   }
<YYINITIAL> ">"                 { return symbol(Sym.SUP);    }
<YYINITIAL> "<"                 { return symbol(Sym.INF);    }

//Opérateur d'affectation
<YYINITIAL> "="                 { return symbol(Sym.AFF); }

//Délimiteurs
<YYINITIAL> "("                 { return symbol(Sym.PAR_OUV);      }
<YYINITIAL> ")"                 { return symbol(Sym.PAR_FER);      }
<YYINITIAL> "{"                 { return symbol(Sym.ACCOLADE_OUV); }
<YYINITIAL> "}"                 { return symbol(Sym.ACCOLADE_FER); }
<YYINITIAL> ","                 { return symbol(Sym.VIRGULE);      }
<YYINITIAL> ";"                 { return symbol(Sym.POINT_VIRG);   }

//Constante entière
<YYINITIAL> {ENTIER}            {
                                    return symbol(Sym.CONST_ENT,
                                        Integer.parseInt(yytext()));
                                }

//Identificateur
<YYINITIAL> {IDENT}             {
                                    return symbol(Sym.IDF, yytext());
                                }

//Caractère non reconnu
<YYINITIAL> [^]                 {
                                    System.err.println(
                                        "Erreur lexicale ligne " + (yyline+1) +
                                        ", colonne " + (yycolumn+1) +
                                        " : caractère inconnu '" + yytext() + "'"
                                    );
                                }