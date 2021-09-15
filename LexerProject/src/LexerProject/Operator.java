package LexerProject;

public class Operator extends Token {

    public String IDValue = "";

    public Operator(int t, String a) {
        super(t, a);
    }

    public final static Operator
        AND = new Operator(Tag.AND, "&&"),
        //BASE_TYPE = new Operator(Tag.BASE_TYPE, ""),
        BREAK = new Operator(Tag.BREAK, "break"),
        DO = new Operator(Tag.DO, "do"),
        ELSE = new Operator(Tag.ELSE, "else"),
        EQ = new Operator(Tag.EQ, "=="),
        FALSE = new Operator(Tag.FALSE, "false"),
        FOR = new Operator(Tag.FOR, "for"),
        GE = new Operator(Tag.GE, ">="),
        //ID = new Operator(Tag.ID, "&&"),
        IF = new Operator(Tag.IF, "&&"),
        LE = new Operator(Tag.LE, "&&"),
        NE = new Operator(Tag.NE, "&&"),
        OR = new Operator(Tag.OR, "&&"),
        REAL = new Operator(Tag.REAL, "&&"),
        TRUE = new Operator(Tag.TRUE, "&&"),
        WHILE = new Operator(Tag.WHILE, "&&");



    public Operator ID = new Operator(Tag.ID, IDValue);

    public void setIDValue(String input) {
        this.IDValue = input;
    }
    

    public String toString() {
        return String.format("%d", operatorValue);
    }
}
