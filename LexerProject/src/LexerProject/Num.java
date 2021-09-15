package LexerProject;

public class Num extends Token{

    public Num(int t, int a) {
        super(t, a);
    }

    public String toString() {
        return String.format("%d", intValue);
    }
}
