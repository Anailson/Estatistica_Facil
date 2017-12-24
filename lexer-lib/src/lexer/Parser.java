package lexer;

import exception.TokenException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Parser {

    private Map<Integer, SyntacticParser> parsers;

    public Parser() {
        this.parsers = new ConcurrentHashMap<>();
    }

    public void register(int key, SyntacticParser parser){
        parsers.put(key, parser);
    }

    public IBaseOperation analyse(String sourceCode) throws TokenException {

        ArrayList<Token> tokens = lexical(sourceCode);
        if(tokens.size() == 0) throw new TokenException(new Token(Token.ANY, "", 0), TokenException.CMD_EMPTY);

        Token actual = tokens.get(0);
        SyntacticParser parser = parsers.get(actual.getType());
        if(parser == null) throw new TokenException(actual, TokenException.CMD_UNEXPECTED_INITIAL);

        parser.setTokens(tokens);
        actual = parser.eat(actual.getType());
        return parser.parse(actual);
    }

    private ArrayList<Token> lexical(String sourceCode) throws TokenException{

        ArrayList<Token> tokens = new ArrayList<>();
        try {
            sourceCode += " ";
            Lexer scanner = new Lexer(new StringReader(sourceCode));
            Token token;

            while ((token = scanner.next()) != null) {
                if(token.isInvalid()) throw new TokenException(token, TokenException.CMD_UNKNOWN);
                System.out.println(token);
                tokens.add(token);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }

    private IBaseOperation getOperation(int value, int position){

        return new IBaseOperation() {
            @Override
            public int id() {
                return value;
            }

            @Override
            public Token getValue(int type) {
                return new Token(type, Token.name(type), position);
            }
        };
    }

    public interface IBaseOperation {

        int id();

        Token getValue(int type) throws Exception;
    }
}