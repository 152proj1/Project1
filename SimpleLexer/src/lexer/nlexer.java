package lexer;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class nlexer {

    public static HashMap<String, Token> tokens = new HashMap<String, Token>();
    public static int rawReadValue;
    public static int currentLine = 1;

    static StringBuffer nextWord = new StringBuffer();
    static String specialCase;
    public static char lookAhead;
    public static char currentChar;

    public static boolean EOFflag = false;
    public static boolean done = false;

    static String stops = ";!|&><+-*/}{)(=";

    public static void main(String[] args) throws IOException {
        System.out.println("\n      ***  START  ***\n");

        //initializes hashMap of tokens 
        Token.initTokenTable(tokens); 

        // open the file
        File f = new File("test_file.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        // call readChar to initialize first values
        readChar(br);

        System.out.println("Current Line: " + currentLine + "\n");
        //loop until the function flags a stop
        do {
            printToken(getNextToken(br));
        } while (!EOFflag);

        //close file
        br.close();
        System.out.println("      ***  DONE  ***\n");
    }

    public static Token getNextToken(BufferedReader b) throws IOException {

        nextWord.delete(0, nextWord.length()); //sets nextWord to empty string so the function can start scanning the token
        readChar(b);
        specialCase = "" + currentChar + lookAhead; //combines currentChar and lookAhead to look for special case tokens like == or ||



        // These if statements set the nextWord to the next token value.
        if (rawReadValue != -1) {
            if ((currentChar == '=' || currentChar == '<' || currentChar == '>' || currentChar == '!')) {
                if (lookAhead == '=') {
                    nextWord.append(specialCase);
                    readChar(b);
                } else {
                    nextWord.append(currentChar);
                }
            } else if (currentChar == '&') {
                if (lookAhead == '&') {
                    nextWord.append(specialCase);
                    readChar(b);
                } else {
                    nextWord.append(currentChar);
                }
            } else if (currentChar == '|') {
                if (lookAhead == '|') {
                    nextWord.append(specialCase);
                    readChar(b);
                } else {
                    nextWord.append(currentChar);
                }
            } else {
                while (!isDelim(lookAhead)) {
                    nextWord.append(currentChar);
                    if (tokens.containsKey("" + nextWord)) {
                        return tokens.get("" + nextWord);
                    }
                    readChar(b);
                }
                nextWord.append(currentChar);
            }
        }
    

        if (done) {
            System.out.println("        EOF token");
            EOFflag = true;
            return Token.EOF;
        } else if (tokens.containsKey("" + nextWord)) { // return the token if it's recognized from the predefined tokens table
            return tokens.get("" + nextWord);
        } else if (isNUM("" + nextWord)) { // Add logic for integer value in "isNUM" function below
            int n;
            n = Integer.parseInt(""+nextWord);
            return new Token(n);
        } else if (isREAL("" + nextWord)) { // Add logic for integer value in "isNUM" function below
            float f = Float.parseFloat(""+nextWord);
            return new Token(f);
        } else if (rawReadValue == -1) { // Add logic for final word
            System.out.println("        ending");
            done = true;
            //System.out.println("nextWord: " + nextWord);
            //System.out.println("currentChar: " + currentChar);
            //System.out.println("lookAhead: " + lookAhead);
            return new Token("" + nextWord);
        } 
        return new Token("" + nextWord);
    }

    // Sets currentChar, rawReadValue, and lookAhead
    static void readChar(BufferedReader b) throws IOException {
        currentChar = lookAhead; // the currentChar actually records the lookAhead that was read in previously.
        rawReadValue = b.read(); //rawReadValue records actual character number, so we can find EOF with rawReadValue == -1.
        lookAhead = (char) rawReadValue; //sets char lookAhead to character of whatever rawReadValue was just read in.
        if (isSpace(currentChar)) {
            readChar(b);
        }
    }


    // boolean flag to separate tokens, returns true if the lookAhead is a "space," single token, or eof.
    static boolean isDelim(char c) throws IOException { 
        if (isSpace(c) || stops.contains("" + c) || rawReadValue == -1)
            return true;
        return false;
    }


    // another flag to separate tokens, but put into a different function for specific use cases.
    static boolean isSpace(char c) throws IOException {
        switch (c) {
            case '\n':
                currentLine++;
                System.out.println("Current Line: " + currentLine + "\n");
                return true;
            case '\t':
                return true;
            case ' ':
                return true;
        }
        return false;
    }


    // returns true if input string is an integer value
    public static boolean isNUM(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    
    // returns true if input string is a float value
    public static boolean isREAL(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    // prints token in the form "token name" + "token value"
    public static void printToken(Token t) throws IOException {
        System.out.print("  " + t.name);
        if (t.name.equals("REAL"))
            System.out.println("    " + t.floatValue);
        else if (t.name.equals("NUM"))
            System.out.println("    " + t.intValue);
        else
            System.out.println("    " + t.IDValue);
        System.out.println();
    }
}


