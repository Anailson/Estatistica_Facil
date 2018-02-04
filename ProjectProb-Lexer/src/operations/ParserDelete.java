package operations;

import exception.TokenException;
import lexer.Parser;
import lexer.SyntacticParser;
import lexer.Token;

public class ParserDelete extends SyntacticParser implements Parser.IBaseOperation{

/*
    del col <number> row <number>
    {DELETE}{COLUMN}{NUMBER}{ROW}{NUMBER}
    {DELETE}{COLUMN}{NUMBER}*/

    private Token col, row;

    @Override
    protected Parser.IBaseOperation parse(Token token) throws TokenException {
        eat(Token.COLUMN);
        col = eat(Token.NUMBER);
        if(hasTokens()){
            eat(Token.ROW);
            row = eat(Token.NUMBER);
        }else {
            row = new Token(Token.EMPTY, "", col.getPosition() + 1);
        }
        return this;
    }

    @Override
    public int id() {
        return Token.DELETE;
    }

    @Override
    public Token getValue(int type) {
        switch (type){
            case Token.COLUMN: return  col;
            case Token.ROW: return row;
        }
        return new Token(Token.ANY, Token.name(Token.ANY), 0);
    }

}
