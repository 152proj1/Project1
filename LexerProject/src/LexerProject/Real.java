package LexerProject;

public class Real extends Token{
    
    public Real(int t, float fl) {
        super(t, fl);
    }

    public String toString() {
        return String.format("%d", floatValue);
    }
}
