public class Token {
public enum TokenType {
    // Keywords
    MATH, ADD, SUBTRACT, MULTIPLY, AND, OR, NOT, XOR, COPY, HALT, BRANCH, JUMP, CALL,
    PUSH, LOAD, RETURN, STORE, PEEK, POP, INTERRUPT, EQUAL, UNEQUAL, GREATER, LESS,
    GREATER_OR_EQUAL, LESS_OR_EQUAL, SHIFT, LEFT, RIGHT,
    // Data types
    REGISTER, NUMBER, IDENTIFIER, STRING_LITERAL,
    // Symbols
    COMMA, SEMICOLON, COLON, OPEN_PAREN, CLOSE_PAREN, OPEN_BRACE, CLOSE_BRACE,
    // Handles the new line
    NEWLINE
}
private TokenType type;
    private String lexeme;
    private int line;
    private int position;

    public Token(TokenType type, String lexeme, int line, int position) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.position = position;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return type + " " + lexeme + " at " + line + ":" + position;
    }
}
