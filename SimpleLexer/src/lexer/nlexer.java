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
    public static char lookAhead;
    public static char currentChar;

    static char prevChar;
    

    public static boolean EOFflag = false;
    public static boolean found = false;
    public static boolean done = false;

    static String stops = ";!|&><+-*/}{)(=";

    public static void main(String[] args) throws IOException {
        System.out.println("\n      ***  START  ***\n");

        Token.initTokenTable(tokens);

        File f = new File("test_file.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        readChar(br);
        do  {
            printToken(getNextToken(br));
        } while (!done);
        br.close();
        System.out.println("      ***  DONE  ***\n");
    }

    public static Token getNextToken(BufferedReader b) throws IOException {
        readWord(b);
        if (EOFflag) {
            done = true;
            System.out.println("            spc EOF flagged");
            return Token.EOF;
        }

        if(tokens.containsKey(""+nextWord)) {
            //System.out.println("Token matched ");
            return tokens.get("" + nextWord);
        } //else if {

        //}
        //System.out.println("    returning nextWord Token: " + nextWord);
        return new Token("" + nextWord);

    }

    public static void readWord(BufferedReader b) throws IOException {
        nextWord.delete(0, nextWord.length());
        readChar(b);

        String specialCase = "" + currentChar + lookAhead;

        if (!EOFflag) {
            if (currentChar == '=' || currentChar == '<' || currentChar == '>' || currentChar == '!') {
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
                    prevChar = currentChar;
                    nextWord.append(currentChar);
                    readChar(b);
                }
                    nextWord.append(currentChar);
            }
        } else {
            System.out.println(" EOF flagged");
            found = true;
            nextWord.append(currentChar);
        }
    }

    static void readChar(BufferedReader b) throws IOException {
        currentChar = lookAhead;
        rawReadValue = b.read();
        lookAhead = (char) rawReadValue;
        if (rawReadValue == -1) {
            EOFflag = true;
        } else if (isSpace(currentChar)) {
            readChar(b);
        } 
    }

    static boolean isDelim(char c) throws IOException {
        if (isSpace(c) || stops.contains("" + c)) return true;
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
