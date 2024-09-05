import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private StringHandler stringHandler;
    private int lineNumber;
    private int charPosition;
    private List<Token> tokens;
    private Map<String, Token.TokenType> keywordMap;
    private Map<String, Token.TokenType> twoCharSymbolMap;
    private Map<Character, Token.TokenType> oneCharSymbolMap;

    public Lexer(String input) {
        this.stringHandler = new StringHandler(input);
        this.lineNumber = 0;
        this.charPosition = 0;
        this.tokens = new ArrayList<>();

        initializeKeywordMap();
        initializeSymbolMaps();
    }

    //New keyword map for assembler
    private void initializeKeywordMap() {
        keywordMap = new HashMap<>();
        String[] keywords = {
            "math", "add", "subtract", "multiply", "and", "or", "not", "xor", "copy", "halt",
            "branch", "jump", "call", "push", "load", "return", "store", "peek", "pop", "interrupt",
            "equal", "unequal", "greater", "less", "greaterOrEqual", "lessOrEqual", "shift", "left", "right"
        };
        for (String keyword : keywords) {
            keywordMap.put(keyword, Token.TokenType.valueOf(keyword.toUpperCase()));
        }
    }

    //Symbol Maps
    private void initializeSymbolMaps() {
        twoCharSymbolMap = new HashMap<>();
        twoCharSymbolMap.put(">=", Token.TokenType.GREATER_OR_EQUAL);
        twoCharSymbolMap.put("<=", Token.TokenType.LESS_OR_EQUAL);
        twoCharSymbolMap.put("==", Token.TokenType.EQUAL);
        twoCharSymbolMap.put("!=", Token.TokenType.UNEQUAL);
        twoCharSymbolMap.put("&&", Token.TokenType.AND);
        twoCharSymbolMap.put("||", Token.TokenType.OR);

        oneCharSymbolMap = new HashMap<>();
        oneCharSymbolMap.put('+', Token.TokenType.ADD);
        oneCharSymbolMap.put('-', Token.TokenType.SUBTRACT);
        oneCharSymbolMap.put('*', Token.TokenType.MULTIPLY);
        oneCharSymbolMap.put(';', Token.TokenType.SEMICOLON);
        oneCharSymbolMap.put(':', Token.TokenType.COLON);
        oneCharSymbolMap.put(',', Token.TokenType.COMMA);
        oneCharSymbolMap.put('(', Token.TokenType.OPEN_PAREN);
        oneCharSymbolMap.put(')', Token.TokenType.CLOSE_PAREN);
    }

    public List<Token> Lex() {
        while (!stringHandler.IsDone()) {
            char currentChar = stringHandler.Peek(0);

            if (Character.isWhitespace(currentChar)) {
                handleWhitespace();
            } else if (Character.isDigit(currentChar) || (currentChar == '-' && Character.isDigit(stringHandler.Peek(1)))) {
                processDigits();
            } else if (Character.isLetter(currentChar)) {
                processWord();
            } else if (twoCharSymbolMap.containsKey(stringHandler.PeekString(2))) {
                processTwoCharSymbol();
            } else if (oneCharSymbolMap.containsKey(currentChar)) {
                processOneCharSymbol();
            } 
        }
        return tokens;
    }

    //WhiteSpace
    private void handleWhitespace() {
        while (Character.isWhitespace(stringHandler.Peek(0))) {
            if (stringHandler.Peek(0) == '\n') {
                lineNumber++;
                tokens.add(new Token(Token.TokenType.NEWLINE, null, lineNumber, charPosition));
            }
            stringHandler.GetChar();  
            charPosition++;
        }
    }
    //Process Word
    private void processWord() {
        StringBuilder wordBuilder = new StringBuilder();
        while (!stringHandler.IsDone() && (Character.isLetter(stringHandler.Peek(0)) || stringHandler.Peek(0) == '_')) {
            char currentChar = stringHandler.GetChar();
            wordBuilder.append(currentChar);
            charPosition++;
        }
        String word = wordBuilder.toString().toLowerCase();
        Token.TokenType type = keywordMap.getOrDefault(word, Token.TokenType.IDENTIFIER);
        tokens.add(new Token(type, word, lineNumber, charPosition - word.length()));
    }
    //Process DIgits 
    private void processDigits() {
        StringBuilder numberBuilder = new StringBuilder();
        if (stringHandler.Peek(0) == '-') {
            numberBuilder.append(stringHandler.GetChar());  //negative numbers
            charPosition++;
        }
       
    
    }

    //One character Symbol
    private void processOneCharSymbol() {
        char currentChar = stringHandler.GetChar();
        Token.TokenType tokenType = oneCharSymbolMap.get(currentChar);
        if (tokenType != null) {
            tokens.add(new Token(tokenType, String.valueOf(currentChar), lineNumber, charPosition));
        } else {
            throw new RuntimeException("Not a one character symbol ");
        }
        charPosition++;
    }
    
    //Two Character Symbol
    private void processTwoCharSymbol() {
        String symbol = stringHandler.PeekString(2);
        Token.TokenType tokenType = twoCharSymbolMap.get(symbol);
        if (tokenType != null) {
            tokens.add(new Token(tokenType, symbol, lineNumber, charPosition));
            stringHandler.GetChar();  //first character of the two-character symbol
            stringHandler.GetChar();  //second character of the two-character symbol
            charPosition += 2;
        } else {
            processOneCharSymbol();  //process the first char as a single symbol
        }
    }



}