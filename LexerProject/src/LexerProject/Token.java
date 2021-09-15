//  A "Token" needs two pieces of information, its Tag and Value. 
//  The Tag will contain the Token's identification, 
//  so the parser knows what kind of object it is looking at.
//  The Value contains the actual content/meaning of the input.
//  Tag and Value pairs will be saved in extended classes of the Token class.
//  Child classes will be: "Real" for float numbers, "Num" for int numbers, 
//  and "Operator" for all other valid inputs.


package LexerProject;

public class Token {

    // common "tag" variable for all child classes
    public int tag; 

    public String operatorValue = "";
    public float floatValue;
    public int intValue;

    // constructors for child classes to override
    public Token(int t, String a) {
        tag = t;
        operatorValue = a;
    }

    public Token(int t, float fl) {
        tag = t;
        floatValue = fl;
    }

    public Token(int t, int a) {
        tag = t;
        intValue = a;
    }

    
    
    public String toString() { 
        return String.format("%d", tag);
    }



}