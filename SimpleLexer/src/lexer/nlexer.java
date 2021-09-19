package lexer;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.HashMap;

public class nlexer {

    public static HashMap<String, Token> tokens = new HashMap<String, Token>();
    public static int rawReadValue;
    public static int currentLine = 0;

    public static char nextChar;
    static StringBuffer nextWord = new StringBuffer();
    public static char prevChar;
    static StringBuffer prevWord = new StringBuffer();
    static String stops = ";!|&><+-*/}{)(=";

    static boolean found = false;
    static boolean EOFflag = false;
    static boolean scanning = false;
    static boolean specFlag = false;
 
    static StringBuffer specialCase = new StringBuffer();

    public static void main(String[] args) throws IOException {
        System.out.println("\n      ***  START  ***\n");
        File f = new File("test_file.txt"); // Creation of File Descriptor for input file
        FileReader fr = new FileReader(f); // Creation of File Reader object
        BufferedReader br = new BufferedReader(fr);

        Token.initTokenTable(tokens);
        readChar(br);
        do {
            System.out.println();
            //getNextToken(br);
            printToken(getNextToken(br));
            found = false;
        }while(!EOFflag);
        

        System.out.println("\n      ***  DONE  ***");
    }

    static Token getNextToken(BufferedReader b) throws IOException {
        if (rawReadValue == -1) {
            found = true;
            EOFflag=true;
            //System.out.println("EOF"); //debug
            return Token.EOF;
        }
 
        StringBuffer nullString = new StringBuffer();
        nullString.append("");
        nextWord = nullString;
        prevWord = nextWord;

        nextWord.append(nextChar);



        do {
            String sWord;
            //prevWord = nextWord;
            //if (specFlag) {
            //    //System.out.println("**RETURNING : " + nextWord);
            //    sWord = "" + prevChar;
            //    specFlag = false;
            //}
            //else {
                sWord =  "" + nextWord;
            //}
            //prevWord = nextWord;
            if (rawReadValue == -1) {
                found = true;
                return Token.EOF;
            } else if (tokens.containsKey(sWord)) {
                skipDelim(b);
                //readChar(b);
                found = true;
                System.out.println("**Token matched: " + nextWord);
                return tokens.get(sWord);
            } else {
                    prevWord = nextWord;
                    readChar(b);
                    if (isDelim(nextChar)) {
                        //System.out.println("delim found: " + nextChar);
                        found = true;
                        System.out.println("**IDValue set: " + prevWord);
                        skipDelim(b);
                        //System.out.println("appending: " + nextWord + " + " + nextChar);
                        return new Token("" + prevWord);
                    }
                    System.out.println("appending: " + nextWord + " + " + nextChar);
                    nextWord.append(nextChar);
                }
        } while (!found);
        return Token.nullToken;
    }

    static void readChar(BufferedReader b) throws IOException {
        rawReadValue = b.read();
        nextChar = (char) rawReadValue;
    }

    // public static void System.out.println();

    static void skipDelim(BufferedReader b) throws IOException {  

        readChar(b);       
    
        if (nextChar == ' ') {
            currentLine++;
            skipDelim(b);
        } if (nextChar == '\n') {
            currentLine++;
            skipDelim(b);
        } else if (nextChar == '\t') {
            skipDelim(b);
        }
        //System.out.println("char after delim: " + nextChar); //debug
    }

    static boolean isDelim(char c) {
        specFlag = false;


        if (c == ' ') {
            return true;
        } else if (c == '\t') {
            return true;
        } else if (c == '\n') {
            currentLine++;
            return true;
        } else if (stops.contains(""+nextChar)) {
            System.out.println("SPECIAL: " + nextChar); //debug
            prevChar = nextChar;
            System.out.println("prevChar set to: " + prevChar); //debug
            specFlag = true;
            return true;
        } else {
            return false;
        }
    }

    public static void printToken(Token t) throws IOException {
        //System.out.println("Token: ");
        //System.out.println("    tag: " + t.tag);
        System.out.print("" + t.name);
        if (t.name.equals("REAL"))
            System.out.println("    "+ t.floatValue);
        else if (t.name.equals("NUM"))
            System.out.println("    " + t.intValue);
        else
            System.out.println("    " + t.IDValue);
        System.out.println();
    }
}
