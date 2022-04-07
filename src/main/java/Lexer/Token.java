package Lexer;

public class Token {
    public SemanticValue value;
    public String name;
    int line;

    public Token(SemanticValue value, String name, int line) {
        this.value = value;
        this.name = name;
        this.line = line;
    }
}
