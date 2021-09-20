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
            getNextToken(br);
        } while (!done);

        br.close();

        System.out.println("      ***  DONE  ***\n");
    }

    public static Token getNextToken(BufferedReader b) throws IOException {
        // System.out.println("\nGetting Next Token: ");
        // System.out.println(" currentChar: " + currentChar);
        readWord(b);
        if (EOFflag) {
            done = true;
            System.out.println("            EOF flagged");
            return Token.EOF;
        }
        System.out.println("    returning nextWord Token: " + nextWord);
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
                    nextWord.append(currentChar);
                    readChar(b);
                }
                nextWord.append(currentChar);
            }
        } else {
            nextWord.append(currentChar);
        }
        // if (EOFflag) System.out.println(" EOF flagged");
    }

    static void readChar(BufferedReader b) throws IOException {
        currentChar = lookAhead;
        rawReadValue = b.read();
        lookAhead = (char) rawReadValue;
        if (rawReadValue == -1) {
            EOFflag = true;
            // System.out.println(" EOF flagged");
        } else if (isSpace(currentChar)) {
            readChar(b);
        } else {
            // System.out.println(" currentChar set to: " + currentChar);
            // System.out.println(" lookAhead set to: " + lookAhead);
        }

    }

    static boolean isDelim(char c) throws IOException {
        switch (c) {
            case '\n':
                currentLine++;
                return true;
            case '\t':
                return true;
            case ' ':
                return true;
        }

        if (stops.contains("" + c))
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
}
