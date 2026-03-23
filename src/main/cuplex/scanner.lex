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
<YYINITIAL> "int"               { return symbol(sym.TYPE_INT);  }
<YYINITIAL> "void"              { return symbol(sym.TYPE_VOID); }

//Mots-clés de contrôle
<YYINITIAL> "si"                { return symbol(sym.SI);      }
<YYINITIAL> "sinon"             { return symbol(sym.SINON);   }
<YYINITIAL> "tantque"           { return symbol(sym.TANTQUE); }
<YYINITIAL> "retour"            { return symbol(sym.RETOUR);  }

//Mots-clés E/S
<YYINITIAL> "ecrire"            { return symbol(sym.ECRIRE); }
<YYINITIAL> "lire"              { return symbol(sym.LIRE);   }

//Opérateurs arithmétiques
<YYINITIAL> "+"                 { return symbol(sym.PLUS);  }
<YYINITIAL> "-"                 { return symbol(sym.MOINS); }
<YYINITIAL> "*"                 { return symbol(sym.MUL);   }
<YYINITIAL> "/"                 { return symbol(sym.DIV);   }

//Opérateurs de comparaison (2 car. avant 1 car.)
<YYINITIAL> ">="                { return symbol(sym.SUP_EG); }
<YYINITIAL> "<="                { return symbol(sym.INF_EG); }
<YYINITIAL> "=="                { return symbol(sym.EGAL);   }
<YYINITIAL> "!="                { return symbol(sym.DIFF);   }
<YYINITIAL> ">"                 { return symbol(sym.SUP);    }
<YYINITIAL> "<"                 { return symbol(sym.INF);    }

//Opérateur d'affectation
<YYINITIAL> "="                 { return symbol(sym.AFF); }

//Délimiteurs
<YYINITIAL> "("                 { return symbol(sym.PAR_OUV);      }
<YYINITIAL> ")"                 { return symbol(sym.PAR_FER);      }
<YYINITIAL> "{"                 { return symbol(sym.ACCOLADE_OUV); }
<YYINITIAL> "}"                 { return symbol(sym.ACCOLADE_FER); }
<YYINITIAL> ","                 { return symbol(sym.VIRGULE);      }
<YYINITIAL> ";"                 { return symbol(sym.POINT_VIRG);   }

//Constante entière
<YYINITIAL> {ENTIER}            {
                                    return symbol(sym.CONST_ENT,
                                        Integer.parseInt(yytext()));
                                }

//Identificateur
<YYINITIAL> {IDENT}             {
                                    return symbol(sym.IDF, yytext());
                                }

//Caractère non reconnu
<YYINITIAL> [^]                 {
                                    System.err.println(
                                        "Erreur lexicale ligne " + (yyline+1) +
                                        ", colonne " + (yycolumn+1) +
                                        " : caractère inconnu '" + yytext() + "'"
                                    );
                                }