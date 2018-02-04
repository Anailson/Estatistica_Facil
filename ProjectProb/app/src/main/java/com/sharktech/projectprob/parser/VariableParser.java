package com.sharktech.projectprob.parser;

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
        ERR_COL_INDEX, ERR_ROW_INDEX
    }

    public VariableParser() {
        this.mParser = new Parser();
        this.mResult = new IParserResult() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError(Error error, Token token) {}
        };
    }

    public void setParserResultListener(IParserResult result) {
        if(result != null) this.mResult = result;
    }

    public void analyse(String source) {

        VariableOperation operation = new VariableOperation(mResult);
        mParser.register(Token.NEW, new ParserNew());
        mParser.register(Token.ADD, new ParserAdd());
        mParser.register(Token.EDIT, new ParserEdit());
        mParser.register(Token.DELETE, new ParserDelete());

        try {
            Parser.IBaseOperation parser = mParser.analyse(source);
            switch (parser.id()) {
                case Token.NEW: operation.finish((ParserNew) parser); break;
                case Token.ADD: operation.finish((ParserAdd) parser); break;
                case Token.EDIT: operation.finish((ParserEdit) parser); break;
                case Token.DELETE: operation.finish((ParserDelete) parser); break;
            }

        } catch (TokenException e) {
            mResult.onError(Error.ERR_GENERAL, e.getToken());
        }
    }

    public interface IParserResult {

        void onSuccess();

        void onError(Error error, Token token);
    }
}