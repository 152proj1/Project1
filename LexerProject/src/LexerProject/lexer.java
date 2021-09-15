package LexerProject;

import java.io.IOException;

public class lexer {
    char nextChar = ' ';
    StringBuffer b = new StringBuffer();

    void getNextChar() throws IOException {
        nextChar = (char) System.in.read();
    }

    boolean readch(char c) throws IOException {
        readch();
        if( peek != c ) return false;
        peek = ' ';
        return true;
     }
     
    public void getNextToken() throws IOException {
        for( ; ; getNextChar() ) {
           if( nextChar == ' ' || nextChar == '\t' ) continue;
           else if( nextChar == '\n' );
           else break;
        }
    }
}
