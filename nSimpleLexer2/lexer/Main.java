package lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        // opens file
        File f = new File("test_file.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        Lexer l = new Lexer();

        l.startLexer(br);

        // loop until the function flags a stop
        do {
            l.printToken(l.getNextToken(br)); // using a print function for display purposes
        } while (!l.EOFflag);

        // close file
        br.close();
    }
}
