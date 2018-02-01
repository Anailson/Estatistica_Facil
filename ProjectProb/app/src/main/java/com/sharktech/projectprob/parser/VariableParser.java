package com.sharktech.projectprob.parser;

import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import exception.TokenException;
import lexer.Parser;
import lexer.Token;

public class VariableParser {

    private Parser mParser;

    public enum Error {
        ERR_GENERAL, MATCH_MANY_VALUES, MATCH_NUMBER, MATCH_TEXT, ERR_NUMBER_TEXT, ERR_TEXT_NUMBER,
    }

    public VariableParser() {
        this.mParser = new Parser();
    }

    public void analyse(String source, IParserResult result) {

        mParser.register(Token.ADD, new AddParser());
        try {
            Parser.IBaseOperation op = mParser.analyse(source);
            switch (op.id()) {
                case Token.ADD:
                    add((AddParser) op, result);
                    break;
            }
        } catch (TokenException e) {
            result.onError(e);
        }
    }

    private void add(AddParser parser, IParserResult result) {

        Error err = parser.finish();
        String msg = "";
        if (err == Error.MATCH_TEXT || err == Error.MATCH_NUMBER || err == Error.MATCH_MANY_VALUES) {
            result.onSuccess();
            return;
        } else if (err == Error.ERR_TEXT_NUMBER) {
            msg = "Era esperado um valor nao numerico";
        } else if (err == Error.ERR_NUMBER_TEXT) {
            msg = "Era esperado um valor numerico";
        } else if (err == Error.ERR_GENERAL) {
            msg = "Erro desconhecido";
        }

        Token error = parser.getValue(Token.COLUMN);
        result.onError(new TokenException(error, msg));
    }

    static Error instanceOf(Token token, TableColumn.IVariable var) {
        int type = token.getType();
        Class cls = var.getClass();

        if (type == Token.NUMBER && cls == VariableNumber.class) return Error.MATCH_NUMBER;
        else if (type == Token.TEXT && cls == VariableString.class) return Error.MATCH_TEXT;
        else if (type == Token.NUMBER && cls != VariableNumber.class) return Error.ERR_NUMBER_TEXT;
        else if (type == Token.TEXT && cls != VariableString.class) return Error.ERR_TEXT_NUMBER;
        else if (type == Token.VALUES) return Error.MATCH_MANY_VALUES;
        return Error.ERR_GENERAL;
    }

    public interface IParserResult {

        void onSuccess();

        void onError(TokenException e);
    }
}