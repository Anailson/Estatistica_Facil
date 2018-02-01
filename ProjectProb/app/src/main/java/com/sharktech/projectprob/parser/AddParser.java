package com.sharktech.projectprob.parser;

import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;
import com.sharktech.projectprob.persistence.VariablePersistence;

import exception.TokenException;
import lexer.Parser;
import lexer.SyntacticParser;
import lexer.Token;

class AddParser extends SyntacticParser implements Parser.IBaseOperation {

    private Token mCol, mVal;

    AddParser() {
        this.mCol = mVal = null;
    }

    VariableParser.Error finish() {
        VariablePersistence persistence = VariablePersistence.getInstance();
        int col = Integer.parseInt(mCol.getText()) - 1;
        TableColumn.IVariable variable = persistence.getVariable(col);
        VariableParser.Error err = VariableParser.instanceOf(mVal, variable);

        if (err == VariableParser.Error.MATCH_NUMBER) addNumber(variable);
        else if (err == VariableParser.Error.MATCH_TEXT) addString(variable);
        else if (err == VariableParser.Error.MATCH_MANY_VALUES) {

            String[] values = mVal.getText().replaceAll(" ", "").split(",");

        }
        return err;
    }

    private void addNumber(TableColumn.IVariable variable){
        ((VariableNumber) variable).add(Integer.valueOf(mVal.getText()));
    }

    private void addString(TableColumn.IVariable variable){
        ((VariableString) variable).add(mVal.getText());
    }

    @Override
    protected Parser.IBaseOperation parse(Token token) throws TokenException {
        eat(Token.COLUMN);
        mCol = eat(Token.NUMBER);
        eat(Token.VAL);
        mVal = eat(Token.TEXT, Token.NUMBER, Token.VALUES);
        return this;
    }

    @Override
    public int id() {
        return Token.ADD;
    }

    @Override
    public Token getValue(int type) {
        switch (type) {
            case Token.COLUMN:
                return mCol;
            case Token.VAL:
                return mVal;
        }
        return new Token(Token.ANY, Token.name(Token.ANY), 0);
    }
}
