package operations;

import exception.TokenException;
import lexer.Parser;
import lexer.SyntacticParser;
import lexer.Token;

public class ParserNew extends SyntacticParser implements Parser.IBaseOperation{

    private Token title;

    @Override
    protected Parser.IBaseOperation parse(Token token) throws TokenException {
        eat(Token.COLUMN);
        title = eat(Token.TEXT);
        return this;
    }

    @Override
    public int id() {
        return Token.NEW;
    }

    @Override
    public Token getValue(int type) {
        return type == Token.TEXT ? title : new Token(Token.ANY, Token.name(Token.ANY), 0);
    }
}
