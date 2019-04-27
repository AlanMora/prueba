package analizador.sintactico;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorSintactico {
	
    
    public static String analizadorL(String texto) {
        String patron = ("([\"][^\"]+[\"]|[\"][\"])|([0-9/$?¿#\"]+[a-zA-Z]+|[0-9]+[?¿$#\"]+|[?¿$#\"]+[0-9]+|[?¿$#\"]+)"
                + "|(if$|if[^a-zA-Z0-9\"]|for$|for[^a-zA-Z0-9\"]|do$|do[^a-zA-Z0-9\"]|printf$|printf[^a-zA-Z0-9\"]"
                + "|scanf$|scanf[^a-zA-Z0-9\"]|while$|while[^a-zA-Z0-9)\"]|switch$|switch[^a-zA-Z0-9\"]|case$|case[^a-zA-Z0-9\"]"
                + "|boolean$|boolean[^a-zA-Z0-9\"]|default$|default[^a-zA-Z0-9\"]|private$|private[^a-zA-Z0-9\"]|"
                + "false$|break$|null$|true$|new$|static$|void$|class$|public$|return$)|"
                + "(int |float |char |String |double |long )|"
                + "([0-9]+)|([a-zA-Z_0-9]+)|(==|<=|>=|!=)|(&&)|"
                + "([>|<|=|+|/|-|--|*])|([{|}|(|)|&|,|:|;])");
        String tokenCad, tokenError ,tokenPR, tokenVar, tokenOp;
        String tokenNum, tokenSint, tokenOpDoble, tokenOpLog, tokenTD;
        boolean errorSint = false, abrirPar = false;
        
        Pattern p = Pattern.compile(patron);
        
            Matcher matcher = p.matcher(texto);
            String lex = "", lex2 = "";
            
            while(matcher.find()) {
                tokenCad = matcher.group(1);
                if (tokenCad != null) {
                    System.out.println(" Cadena: " + tokenCad);
                    lex = lex + "CAD ";
                    if (errorSint == false) {
                        lex2 = lex2 + "CAD ";
                    }
                }
                
                tokenError = matcher.group(2);
                if (tokenError != null) {
                    System.out.println(" Error: " + tokenError);
                    lex = lex + "ER ";
                    lex2 = "ER";
                    errorSint = true;
                }
                
                tokenPR = matcher.group(3);
                if (tokenPR != null) {
                    if (tokenPR.equals("while(")) {
                        tokenPR = "while ";
                        abrirPar = true;
                    }
                    if (tokenPR.equals("if(")) {
                        tokenPR = "if ";
                        abrirPar = true;
                    }
                    if (tokenPR.equals("for(")) {
                        tokenPR = "for ";
                        abrirPar = true;
                    }
                    if (tokenPR.equals("printf(")) {
                        tokenPR = "printf ";
                        abrirPar = true;
                    }
                    System.out.println(" Palabra Reservada: " + tokenPR);
                    lex = lex + "PR ";
                    if (errorSint == false) {
                        lex2 = lex2 + tokenPR;
                    }
                }
                
                tokenTD = matcher.group(4);
                if (tokenTD != null) {
                    System.out.println(" Tipo de Dato: " + tokenTD);
                    lex = lex + "TD ";
                    if (errorSint == false) {
                        lex2 = lex2 + tokenTD;
                    }
                }
                
                tokenNum = matcher.group(5);
                if (tokenNum != null) {
                    System.out.println(" Numero: " + tokenNum);
                    lex = lex + "NUM ";
                    if (errorSint == false) {
                        lex2 = lex2 + "NUM ";
                    }
                }
                
                tokenVar = matcher.group(6);
                if (tokenVar != null) {
                    System.out.println(" Variable: " + tokenVar);
                    lex = lex + "VAR ";
                    if (errorSint == false) {
                        lex2 = lex2 + "VAR ";
                    }
                }
                
                tokenOpDoble = matcher.group(7);
                if (tokenOpDoble != null) {
                    System.out.println(" Comparador: " + tokenOpDoble);
                    lex = lex + "COM ";
                    if (errorSint == false) {
                        lex2 = lex2 + "COM ";
                    }
                }
                
                tokenOpLog = matcher.group(8);
                if (tokenOpLog != null) {
                    System.out.println(" Operador Logico: " + tokenOpLog);
                    lex = lex + "OPL ";
                    if (errorSint == false) {
                        lex2 = lex2 + "OPL ";
                    }
                }
                
                tokenOp = matcher.group(9);
                if (tokenOp != null) {
                    System.out.println(" Operador: " + tokenOp);
                    lex = lex + "OP ";
                    if (errorSint == false) {
                        lex2 = lex2 + tokenOp + " ";
                    }
                }
                if (abrirPar == true) {
                    tokenSint = "(";
                    abrirPar = false;
                }else {
                    tokenSint = matcher.group(10);
                }
                if (tokenSint != null) {
                    System.out.println(" Operador Sintactico: " + tokenSint);
                    lex = lex + "OPS ";
                    if (errorSint == false) {
                        lex2 = lex2 + tokenSint + " ";
                    }
                }
                System.out.println();
            }
            System.out.println(lex2);
            return lex2;
    }
    
    public static String analizadorS(String texto){
        if (texto.equals("if ( VAR COM NUM OPL VAR COM NUM ) { int VAR = VAR ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR COM NUM ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR COM NUM ) { String VAR = CAD ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }else if (texto.equals("if ( VAR COM NUM OPL VAR COM NUM ) { String VAR = CAD ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR < NUM ) { String VAR = CAD ; PR ( VAR < NUM ) { int VAR = NUM ; } } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR < NUM ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR < NUM ) { String VAR = CAD ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR < VAR ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        
        else if (texto.equals("if ( VAR > VAR ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR < VAR ) { String VAR = CAD ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR > VAR ) { String VAR = CAD ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR < VAR ) { } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR > VAR ) { } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("if ( VAR COM VAR ) { } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR COM NUM ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR COM NUM OPL VAR COM NUM ) { int VAR = VAR ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR COM NUM ) { String VAR = CAD ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }else if (texto.equals("while ( VAR COM NUM OPL VAR COM NUM ) { String VAR = CAD ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR < NUM ) { String VAR = CAD ; PR ( VAR < NUM ) { int VAR = NUM ; } } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR < NUM ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR < NUM ) { String VAR = CAD ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR < VAR ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR > VAR ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR < VAR ) { String VAR = CAD ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR > VAR ) { String VAR = CAD ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR < VAR ) { } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR > VAR ) { } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("while ( VAR COM VAR ) { } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("for ( int VAR = NUM ; VAR < NUM ; VAR + + ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }else if (texto.equals("for ( int VAR = NUM ; VAR < NUM ; VAR - - ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto; 
        }else if (texto.equals("for(int VAR = NUM ; VAR < NUM; VAR--) {  } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        } else if (texto.equals("for ( int  VAR = NUM ; VAR > NUM ; VAR + + ) { int VAR = NUM ; } ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("for ( int  VAR = NUM ; VAR > NUM ; VAR ++){}")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("int VAR ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("int VAR = NUM ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("int VAR = VAR ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("String VAR ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("String VAR = CAD ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("String VAR = VAR ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("printf ( CAD ) ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("printf ( VAR ) ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("printf ( CAD , NUM ) ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("printf ( CAD , CAD ) ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("scanf ( CAD ) ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("scanf ( VAR ) ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("scanf ( CAD , NUM ) ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("scanf ( CAD , CAD ) ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else if (texto.equals("scanf(CAD , & VAR ) ; ")) {
            texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
        else {
            texto = "\nERROR SINTACTICO.";
            //texto = "\nANALISIS SINTACTICO ACEPTADO.";
            return texto;
        }
    }
    
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Escriba su codigo.");
        String analizar = teclado.nextLine();
        analizar = analizadorL(analizar);
        if (analizar.equals("ER")) {
            System.out.println("ERROR LEXICO");
        }
        else {
            analizar = analizadorS(analizar);
            System.out.println(analizar);
        }
            }
    
}
