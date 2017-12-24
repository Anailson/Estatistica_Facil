package operations;

import exception.TokenException;
import lexer.SyntacticParser;
import lexer.Parser.IBaseOperation;
import lexer.Token;

public class ParserAdd extends SyntacticParser implements IBaseOperation{

    private Token col, val;

    public ParserAdd() {
        this.col = val = null;
    }

    @Override
    protected IBaseOperation parse(Token token) throws TokenException {
        eat(Token.COLUMN);
        col = eat(Token.NUMBER);
        eat(Token.VAL);
        val = eat(Token.TEXT, Token.NUMBER, Token.VALUES);
        return this;
    }

    @Override
    public int id() {
        return Token.ADD;
    }

    @Override
    public Token getValue(int type) {
        switch(type){
            case Token.COLUMN: return col;
            case Token.VAL: return val;

        }
        return new Token(Token.ANY, Token.name(Token.ANY), 0);
    }
}