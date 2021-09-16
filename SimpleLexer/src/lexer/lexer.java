package lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class lexer {

    public static HashMap<String, Token> tokens = new HashMap<String, Token>();
    public static int currentLine = 1;
    static StringBuffer nextWord = new StringBuffer();
    static char nextChar = ' ';
    static int nextReadValue;
    static boolean EOFflag = false;

    public static Token getNextToken(BufferedReader b) throws IOException {


        readWord(b);
        String word = "" + nextWord;
        String content = "";
        System.out.println(nextWord); //debug

        if (word.equals("ID")) {
            readWord(b);
            content = "" + nextWord;
            return new Token(content);
        
        } 
        else if (word.equals("REAL")) {
            readWord(b);
            content = "" + nextWord;
            float RealContent = Float.parseFloat(content);
            return new Token(RealContent);
        } 
        else if (word.equals("NUM")) {
            readWord(b);
            content = "" + nextWord;
            int numContent = Integer.parseInt(content);
            return new Token(numContent);
        } 
        else if (word.equals("BASE_TYPE")) {
            readWord(b);
            content = "" + nextWord;
            return new Token(content);
        } 
        else if (nextReadValue == -1) {
            System.out.println("EOF flagged"); //debug
            EOFflag = true;
            return Token.EOF;
        } 
        else if (tokens.containsKey(word)) {
            return tokens.get(word);
        } 



        /*if (nextReadValue == -1) {
            System.out.println("EOF reached");
            EOFflag = true;
            return Token.EOF;
            
        }*/

        return Token.nullToken;




        
    }

    static void readChar(BufferedReader b) throws IOException {
        char c = ' ';
        System.out.println(nextChar); //debug
        nextReadValue = b.read();
        c = (char) nextReadValue;
        nextChar = c;
        // System.out.println("EOF flagged"); //debug
    }

    static void readWord(BufferedReader b) throws IOException {
        readChar(b);
        StringBuffer word = new StringBuffer();
        do {
            word.append(nextChar);
            readChar(b);
        } while (!isDelimiter(nextChar));

        nextWord = word;
    }

    static boolean isDelimiter(char c) {
        if (c == ' ') {
            return true;
        } else if (c == '\t') {
            return true;
        } else if (c == '\n') {
            currentLine++;
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        File f = new File("test_file.txt"); // Creation of File Descriptor for input file
        FileReader fr = new FileReader(f); // Creation of File Reader object
        BufferedReader br = new BufferedReader(fr);

        Token.initTokenTable(tokens);

        Token t = Token.nullToken;
        t.equals(getNextToken(br));
        //Token t = getNextToken(br);
        System.out.println("" + t.name + " " + t.intValue);
        //System.out.println("" + t.name + " " + t.intValue);


        do {
            t.equals(getNextToken(br));
        } while(!EOFflag);
    }

}
