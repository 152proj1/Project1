package lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;


public class Lexer {

    HashMap<String, Token> tokens = new HashMap<String, Token>();
    int rawReadValue;
    int currentLine = 1;

    StringBuffer nextWord = new StringBuffer();
    String specialCase;
    char lookAhead;
    char currentChar;

    public boolean EOFflag = false;
    boolean done = false;

    public void startLexer(BufferedReader b) throws IOException {
        // initializes hashMap of tokens
        Token.initTokenTable(tokens);

        // call readChar to initialize first values
        readChar(b);
    }

    public Token getNextToken(BufferedReader b) throws IOException {

        // Section 1: these if statements set "nextWord" to the next parsable token in
        // the file.

        nextWord.delete(0, nextWord.length()); // resetting nextWord
        readChar(b);
        specialCase = "" + currentChar + lookAhead; // combines the current and next letters to look for special case
                                                    // tokens like == or ||
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
                while (!isDelim(lookAhead)) { // skips nontokens and moves onto next token
                    nextWord.append(currentChar);
                    if (tokens.containsKey("" + nextWord)) {
                        return tokens.get("" + nextWord);
                    }
                    readChar(b);
                }
                nextWord.append(currentChar);
            }
        }

        // Section 2: Here, the lexer recognizes what kind of Token nextWord should be
        // returned as

        if (done) {
            EOFflag = true;
            return Token.EOF;
        } else if (tokens.containsKey("" + nextWord)) { // return the token if it's recognized from the predefined
                                                        // tokens table
            return tokens.get("" + nextWord);
        } else if (isNUM("" + nextWord)) { // checks if nextWord is an int
            int n = Integer.parseInt("" + nextWord);
            return new Token(n); // returns NUM token
        } else if (isREAL("" + nextWord)) { // checks if nextWord is a float
            float f = Float.parseFloat("" + nextWord);
            return new Token(f); // returns REAL token
        } else if (rawReadValue == -1) { // activates if last token is a single character
            done = true;
            if (tokens.containsKey("" + currentChar)) {
                return tokens.get("" + currentChar);
            } else if (("" + nextWord == "")) {
                return new Token("" + currentChar);
            }
            return Token.EOF;
        } else
            return new Token("" + nextWord); // returns ID token

    }

    // Sets currentChar, rawReadValue, and lookAhead
    public void readChar(BufferedReader b) throws IOException {
        currentChar = lookAhead; // the currentChar actually records the lookAhead that was read in previously.
        rawReadValue = b.read(); // rawReadValue records actual character number, so we can find EOF with
                                 // rawReadValue == -1.
        lookAhead = (char) rawReadValue; // sets char lookAhead to character of whatever rawReadValue was just read in.
        if (isSpace(currentChar)) {
            readChar(b);
        }
    }

    // Boolean flag to separate tokens, returns true if the lookAhead is a space,
    // single token, or eof.
    public boolean isDelim(char c) throws IOException {
        String stops = ";!|&><+-*/}{)(=";
        if (isSpace(c) || stops.contains("" + c) || rawReadValue == -1)
            return true;
        return false;
    }

    // another flag to separate tokens, returns true if lookAhead is a new line,
    // tab, or space.
    public boolean isSpace(char c) throws IOException {
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

    // returns true if input string is an integer value
    public boolean isNUM(String s) {
        try {
            Integer.parseInt(s); // checks if string can be parsed to an integer
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // returns true if input string is a float value
    public boolean isREAL(String s) {
        try {
            Float.parseFloat(s); // checks if string can be parsed to a float
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // prints token in the form "token name" + "token value"
    public void printToken(Token t) throws IOException {
        if (t.name.equals("REAL"))
            System.out.printf("%-13s%-13f\n", t.name, t.floatValue);
        else if (t.name.equals("NUM"))
            System.out.printf("%-13s%-13d\n", t.name, t.intValue);
        else
            System.out.printf("%-13s%-13s\n", t.name, t.IDValue);
        System.out.println();
    }
}
