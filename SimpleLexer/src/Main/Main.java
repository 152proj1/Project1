package Main;

import lexer.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args)  throws IOException{
        System.out.println("\n      ***  START  ***\n");

        File f = new File("test_file.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        nlexer6.startLexer(br);

        // loop until the function flags a stop
        do {
            nlexer6.printToken(nlexer6.getNextToken(br));
        } while (!nlexer6.EOFflag);

        // close file
        br.close();
        System.out.println("      ***  DONE  ***\n");
    }
}
