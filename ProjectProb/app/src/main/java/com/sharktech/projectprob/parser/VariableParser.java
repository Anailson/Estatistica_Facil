package com.sharktech.projectprob.parser;

import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import exception.TokenException;
import lexer.Parser;
import lexer.Token;
import parser.ParserAdd;
import parser.ParserDelete;
import parser.ParserEdit;
import parser.ParserNew;

public class VariableParser {

    private Parser mParser;
    private IParserResult mResult;

    public enum Error {
        ERR_GENERAL, MATCH_MANY_VALUES, MATCH_NUMBER, MATCH_TEXT, ERR_NUMBER_TEXT, ERR_TEXT_NUMBER,
    }

    public VariableParser() {
        this.mParser = new Parser();
        this.mResult = new IParserResult() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError(TokenException e) {}
        };
    }

    public void setParserResultListener(IParserResult result) {
        if(result != null) this.mResult = result;
    }

    public void analyse(String source) {

        VariableOperation operation = new VariableOperation();
        mParser.register(Token.NEW, new ParserNew());
        mParser.register(Token.ADD, new ParserAdd());
        mParser.register(Token.EDIT, new ParserEdit());
        mParser.register(Token.DELETE, new ParserDelete());

        Error error = Error.ERR_GENERAL;
        try {
            Parser.IBaseOperation op = mParser.analyse(source);
            switch (op.id()) {
                case Token.ADD: error = operation.add((ParserAdd) op); break;
            }

        } catch (TokenException e) {
            mResult.onError(e);
        }

        result(error, operation.getLastToken());
    }

    private void result(Error err, Token lastToken) {

        String msg = "";
        if (err == Error.MATCH_TEXT || err == Error.MATCH_NUMBER || err == Error.MATCH_MANY_VALUES) {
            mResult.onSuccess();
            return;
        } else if (err == Error.ERR_TEXT_NUMBER) {
            msg = "Era esperado um valor nao numerico";
        } else if (err == Error.ERR_NUMBER_TEXT) {
            msg = "Era esperado um valor numerico";
        } else if (err == Error.ERR_GENERAL) {
            msg = "Erro desconhecido";
        }

        mResult.onError(new TokenException(lastToken, msg));
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