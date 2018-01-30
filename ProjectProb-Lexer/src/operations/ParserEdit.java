package operations;

import exception.TokenException;
import lexer.Parser;
import lexer.SyntacticParser;
import lexer.Token;

public class ParserEdit extends SyntacticParser implements Parser.IBaseOperation{

    private Token col, row, val;

    @Override
    protected Parser.IBaseOperation parse(Token token) throws TokenException {

        eat(Token.COLUMN);
        col = eat(Token.NUMBER);
        eat(Token.ROW);
        row = eat(Token.NUMBER);
        eat(Token.VAL);
        val = eat(Token.NUMBER, Token.TEXT);
        return this;
    }

    @Override
    public int id() {
        return Token.EDIT;
    }

    @Override
    public Token getValue(int type) {
        switch (type){
            case Token.COLUMN: return col;
            case Token.ROW: return row;
            case Token.VAL: return val;
        }
        return new Token(Token.ANY, Token.name(Token.ANY), 0);
    }
}
