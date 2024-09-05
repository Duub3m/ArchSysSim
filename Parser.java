import java.util.List;
import java.util.ArrayList;

public class Parser {
    private List<Token> tokens;
    private int position = 0;
    private Token currentToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentToken = tokens.get(0);
    }

    private void consume(Token.TokenType type) {
        if (currentToken.getType() == type) {
            currentToken = tokens.get(++position);
        } else {
            throw new RuntimeException("Runtime Exception");
        }
    }

    private boolean accept(Token.TokenType type) {
        if (currentToken.getType() == type) {
            consume(type);
            return true;
        }
        return false;
    }

    public void parse() {
        while (position < tokens.size()) {
            statement();
        }
    }

    private void statement() {
        if (accept(Token.TokenType.MATH)) {
            mathStatement();
        } else if (accept(Token.TokenType.LOAD)) {
            loadStatement();
        }
        consume(Token.TokenType.NEWLINE);
    }

    private void mathStatement() {
        Token.TokenType operation = currentToken.getType();
        consume(operation); // operations (Add, subtract, )
        RegisterOperation regOp = operationParameters();

        // Assemble a math operation into binary code
        String binaryCode = Assembler.assembleMath(regOp);
        System.out.println("Math operation binary: " + binaryCode);
    }

    private RegisterOperation operationParameters() {
        if (accept(Token.TokenType.TWO_R)) {
            return twoR();
        } else if (accept(Token.TokenType.THREE_R)) {
            return threeR();
        } else {
            throw new RuntimeException("Runtime Exception RegisterOperation");
        }
    }

    private RegisterOperation twoR() {
        int reg1 = parseRegister();
        int reg2 = parseRegister();
        return new RegisterOperation("2R", reg1, reg2); 
    }

    private RegisterOperation threeR() {
        int reg1 = parseRegister();
        int reg2 = parseRegister();
        int reg3 = parseRegister();
        return new RegisterOperation("3R", reg1, reg2, reg3); 
    }

    private int parseRegister() {
        consume(Token.TokenType.REGISTER);
        return Integer.parseInt(currentToken.getValue().substring(1)); 
    }

    private void loadStatement() {
        int number = parseNumber();
        int register = parseRegister();

        // Assemble load operation into binary 
        String binaryCode = Assembler.assembleLoad(number, register);
        System.out.println("Load operation binary: " + binaryCode);
    }

    private int parseNumber() {
        consume(Token.TokenType.NUMBER);
        return Integer.parseInt(currentToken.getValue());
    }
}
