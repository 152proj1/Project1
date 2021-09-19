//  A "Token" needs two pieces of information, its Tag and Value. 
//  The Tag will contain the Token's identification, 
//  
//
//
//
//  Null Token will have Tag 0 for easy identifiaction and error flagging.


package lexer;
import java.util.HashMap;
import java.io.IOException;

public class Token {

    // common "tag" variable for all child classes
    public int tag = 0; 
    public String name = "";

    public String IDValue;
    public float floatValue;
    public int intValue;

    // default constructor for ID Token object
    public Token(String a) {
        tag = 10;
        name = "ID";
        IDValue = a;
    }

    // default constructor for REAL Token object
    public Token(float f) {
        tag = 16;
        name = "REAL";
        floatValue = f;
    }

    // default constructor for NUM Token object
    public Token(int i) {
        tag = 14; // default constructor for ID Token objects
        name = "NUM";
        intValue = i;
    }

    // default constructor for predefined tokens
    public Token(int t, String n, String a) {
        tag = t;
        name = n;
        IDValue = a;
    }


    public static String checkBaseType(String s) {
        switch(s) {
            case "int":
                return "int";
            case "float":
                return "float";
            case "bool":
                return "bool";
        }
        return "fail";
    }

    public void equals(Token t) throws IOException {
        tag = t.tag;
        name = t.name;
        if (name.equals("ID")) IDValue = t.IDValue;
        else if (name.equals("REAL")) floatValue = t.floatValue;
        else if (name.equals("NUM")) intValue = t.intValue;
    }


    public final static Token

        nullToken   = new Token(-1, "empty", "empty"),
        EOF         = new Token(-2, "EOF", "EOF"),

        base_int    = new Token(-3, "BASE_TYPE", "int"),
        base_float  = new Token(-4, "BASE_TYPE", "float"),
        base_bool   = new Token(-5, "BASE_TYPE", "bool"),

        AND         = new Token(1, "AND", "&&"),
        BREAK       = new Token(3, "BREAK", "break"),
        DO          = new Token(4, "DO", "do"),
        ELSE        = new Token(5, "ELSE", "else"),
        EQ          = new Token(6, "EQ", "=="),
        FALSE       = new Token(7, "FALSE", "false"),
        FOR         = new Token(8, "FOR", "for"),
        GE          = new Token(9, "GE", ">="),
        IF          = new Token(11, "IF", "if"),
        LE          = new Token(12, "LE", "<="),
        NE          = new Token(13, "NE", "!="),
        OR          = new Token(15, "OR", "||"),
        TRUE        = new Token(17, "TRUE", "true"),
        WHILE       = new Token(18, "WHILE", "while"),
        semic_      = new Token(19, ";", ";"),
        eq_         = new Token(20, "=", "="),
        greaterTh_  = new Token(21, ">", ">"),
        lessTh_     = new Token(22, "<", "<"),
        openPar_    = new Token(23, "(", "("),
        closePar_   = new Token(24, ")", ")"),
        openBr_     = new Token(25, "{", "{"),
        closeBr_    = new Token(26, "}", "}"),
        plus_       = new Token(27, "+", "+"),
        minus_      = new Token(28, "-", "-"),
        mult_       = new Token(29, "*", "*"),
        slash_      = new Token(30, "/", "/");



    public String print(Token t) { 
        return " " + t.name;
    }

    public static void initTokenTable(HashMap<String, Token> inpMap) {
        inpMap.put("&&", AND);
        inpMap.put("break", BREAK);
        inpMap.put("do", DO);
        inpMap.put("else", ELSE);
        inpMap.put("==", EQ);
        inpMap.put("false", FALSE);
        inpMap.put("for", FOR);
        inpMap.put(">=", GE);
        inpMap.put("if", IF);
        inpMap.put("!=", NE);
        inpMap.put("||", OR);
        inpMap.put("true", TRUE);
        inpMap.put("while", WHILE);
        inpMap.put(";", semic_);
        inpMap.put("=", eq_);
        inpMap.put(">", greaterTh_);
        inpMap.put("<", lessTh_);
        inpMap.put("(", openPar_);
        inpMap.put(")", closePar_);
        inpMap.put("{", openBr_);
        inpMap.put("}", closeBr_);
        inpMap.put("+",plus_);
        inpMap.put("-", minus_);
        inpMap.put("*", mult_);
        inpMap.put("/", slash_);
        inpMap.put("EOF", EOF);

        inpMap.put("nullT", nullToken);
        inpMap.put("EOF", EOF);

        inpMap.put("int", base_int);
        inpMap.put("float", base_float);
        inpMap.put("bool", base_bool);

    }


}