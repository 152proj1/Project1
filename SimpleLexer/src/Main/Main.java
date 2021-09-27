package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import Lexer.*;

public class Main {
    public static void main(String[] args)  throws IOException{
        System.out.println("\n      ***  START  ***\n");

        File f = new File("test_file.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        lexer l = new lexer();

        l.startLexer(br);

        // loop until the function flags a stop
        do {
            l.printToken(l.getNextToken(br));
        } while (!l.EOFflag);

        // close file
        br.close();
        System.out.println("      ***  DONE  ***\n");
    }
}
