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
    static boolean done = false;

    static int count = 0; //debug

    static void readChar(BufferedReader b) throws IOException {
        char c = ' ';
        nextReadValue = b.read();
        //System.out.println("nrv is: " + nextReadValue); //debug
        c = (char) nextReadValue;
        //System.out.println("c is: " + c); //debug
        nextChar = c;
        //System.out.println("nextChar is: " + nextChar); //debug
        //System.out.println("EOF flagged"); //debug
    }

    static void readWord(BufferedReader b) throws IOException {
        readChar(b);
        StringBuffer word = new StringBuffer();
        do {
            if (nextReadValue == -1) {
                EOFflag = true;
                break;
            }
            word.append(nextChar);
            readChar(b);
        } while (!isDelimiter(nextChar));

        /*while(!isDelimiter(nextChar)) {
            word.append(nextChar);
            readChar(b);
        }*/

        nextWord = word;
        //System.out.println("nextWord is: " + nextWord);
        //System.out.println("nextWord is: " + nextReadValue);
        //System.out.println("EOFflag is: " + EOFflag);

    }

    static boolean isDelimiter(char c) {
        if (c == ' ') {
            //System.out.println("dlim found");
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

        do {
            printToken(getNextToken(br));
        } while (!done);


        System.out.println("done");
    }


    static Token getNextToken(BufferedReader b) throws IOException{
        readWord(b);

        //if (EOFflag) return tokens.get("EOF");

        String word = "" + nextWord;
        if (tokens.containsKey(word)) {
            return tokens.get(word);
        }
        else if (word.equals("ID")) {
            readWord(b);
            String content = "" + nextWord;
            return new Token(content);
        
        } 
        else if (word.equals("REAL")) {
            readWord(b);
            //String content = "" + nextWord;
            float RealContent = Float.parseFloat(""+nextWord);
            return new Token(RealContent);
        } 
        else if (word.equals("BASE_TYPE")) {
            readWord(b);
            return new Token("" + nextWord);
        } else if (EOFflag) {
            done = true;
            return tokens.get("EOF");
        }
        return tokens.get("nullT");
    }

    public static void printToken(Token t) throws IOException {
        System.out.println("Token: ");
        System.out.println("    tag: " + t.tag);
        System.out.println("   name: " + t.name);
        if (t.name.equals("REAL")) System.out.println("  value: "+ t.floatValue);
        else if (t.name.equals("NUM")) System.out.println("  value: " + t.intValue);
        else System.out.println("  value: " + t.value);
    }


}
