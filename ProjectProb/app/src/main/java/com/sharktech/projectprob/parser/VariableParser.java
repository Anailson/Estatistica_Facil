package com.sharktech.projectprob.parser;

import android.content.Context;

import com.sharktech.projectprob.R;

import exception.TokenException;
import lexer.Parser;
import lexer.Token;
import parser.ParserAdd;
import parser.ParserDelete;
import parser.ParserEdit;
import parser.ParserNew;

public class VariableParser {

    private Context mContext;
    private Parser mParser;
    private IParserResult mResult;

    public enum Error {
        ERR_GENERAL, MATCH_MANY_VALUES, MATCH_NUMBER, MATCH_TEXT, ERR_NUMBER_TEXT, ERR_TEXT_NUMBER,
    }

    public VariableParser(Context context) {
        this.mContext = context;
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
                case Token.NEW: error = operation.newVar((ParserNew) op); break;
                case Token.ADD: error = operation.add((ParserAdd) op); break;
                case Token.EDIT: error = operation.edit((ParserEdit) op); break;
            }

        } catch (TokenException e) {
            mResult.onError(e);
        }

        result(error, operation.getLastToken());
    }

    private void result(Error err, Token lastToken) {

        int msgResource = -1;
        if (err == Error.MATCH_TEXT || err == Error.MATCH_NUMBER || err == Error.MATCH_MANY_VALUES) {
            mResult.onSuccess();
            return;
        } else if (err == Error.ERR_TEXT_NUMBER) {
            msgResource = R.string.err_text_number;
        } else if (err == Error.ERR_NUMBER_TEXT) {
            msgResource = R.string.err_number_text;
        } else if (err == Error.ERR_GENERAL) {
            msgResource = R.string.err_general;
        }

        mResult.onError(new TokenException(lastToken, mContext.getString(msgResource)));
    }

    public interface IParserResult {

        void onSuccess();

        void onError(TokenException e);
    }
}