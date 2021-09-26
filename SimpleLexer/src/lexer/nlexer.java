package lexer;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class nlexer {

    public static HashMap<String, Token> tokens = new HashMap<String, Token>();
    public static int rawReadValue;
    public static int currentLine = 0;

    static StringBuffer nextWord = new StringBuffer();
    static String specialCase;
    public static char lookAhead;
    public static char currentChar;

    static char prevChar;

    public static boolean EOFflag = false;
    public static boolean found = false;
    public static boolean done = false;
    public static boolean EOFfound = false;
    public static boolean isNUM = false;

    static String stops = ";!|&><+-*/}{)(=";

    public static void main(String[] args) throws IOException {
        System.out.println("\n      ***  START  ***\n");

        Token.initTokenTable(tokens);

        File f = new File("test_file.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        readChar(br);
        do {
            printToken(getNextToken(br));
        } while (!EOFflag);
        br.close();
        System.out.println("      ***  DONE  ***\n");
    }

    public static Token getNextToken(BufferedReader b) throws IOException {

        nextWord.delete(0, nextWord.length()); // = ""
        readChar(b);
        specialCase = "" + currentChar + lookAhead;

        if (rawReadValue != -1) {
            if (currentChar == '=' || currentChar == '<' || currentChar == '>' || currentChar == '!') {
                if (lookAhead == '=') {
                    nextWord.append(specialCase);
                    System.out.println(specialCase);
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
                    prevChar = currentChar;
                    nextWord.append(currentChar);
                    if (tokens.containsKey("" + nextWord)) {
                        return tokens.get("" + nextWord);
                    }
                    readChar(b);
                }
                nextWord.append(currentChar);
            }
        }

        // nextWord = "45"

        if (done) {
            System.out.println("        EOF token");
            EOFflag = true;
            return Token.EOF;
        } else if (tokens.containsKey("" + nextWord)) {
            return tokens.get("" + nextWord);
        } else if (isNUM("" + nextWord)) { // Add logic for integer value in "isNUM" function below
            int n;
            n = Integer.parseInt(""+nextWord);
            return new Token(n);
        } else if (isNUM("" + nextWord)) { // Add logic for integer value in "isNUM" function below
        int n;
        n = Integer.parseInt(""+nextWord);
        return new Token(n);
    } else if (rawReadValue == -1) {
            System.out.println("        ending");
            done = true;
            System.out.println("nextWord: " + nextWord);
            System.out.println("currentChar: " + currentChar);
            System.out.println("lookAhead: " + lookAhead);
            return new Token("" + nextWord);
        }
        return new Token("" + nextWord);
    }

    static void readChar(BufferedReader b) throws IOException {
        currentChar = lookAhead;
        rawReadValue = b.read();
        lookAhead = (char) rawReadValue;
        if (isSpace(currentChar)) {
            readChar(b);
        }
    }

    static boolean isDelim(char c) throws IOException {
        if (isSpace(c) || stops.contains("" + c))
            return true;
        if (rawReadValue == -1)
            return true;
        return false;
    }

    static boolean isSpace(char c) throws IOException {
        switch (c) {
            case '\n':
                currentLine++;
                return true;
            case '\t':
                return true;
            case ' ':
                return true;
        }
        return false;
    }

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

    public static void printToken(Token t) throws IOException {
        System.out.print("" + t.name);
        if (t.name.equals("REAL"))
            System.out.println("    " + t.floatValue);
        else if (t.name.equals("NUM"))
            System.out.println("    " + t.intValue);
        else
            System.out.println("    " + t.IDValue);
        System.out.println();
    }
}
