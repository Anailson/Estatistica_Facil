package operations;

import exception.TokenException;
import lexer.Parser;
import lexer.SyntacticParser;
import lexer.Token;

public class ParserNew extends SyntacticParser implements Parser.IBaseOperation{

    private Token title, val;

    @Override
    protected Parser.IBaseOperation parse(Token token) throws TokenException {
        eat(Token.VAR);
        title = eat(Token.TEXT);
        eat(Token.VAL);
        val = eat(Token.VALUES, Token.NUMBER, Token.TEXT);
        return this;
    }

    @Override
    public int id() {
        return Token.NEW;
    }

    @Override
    public Token getValue(int type) {

        if(type == Token.TEXT) return title;
        else if(type == Token.VAL) return  val;
        return new Token(Token.ANY, Token.name(Token.ANY), 0);
    }
}
