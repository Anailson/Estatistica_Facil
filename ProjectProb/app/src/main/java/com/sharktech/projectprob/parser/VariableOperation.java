package com.sharktech.projectprob.parser;

import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;
import com.sharktech.projectprob.persistence.VariablePersistence;

import lexer.Token;
import parser.ParserAdd;

class VariableOperation {

    private Token mLastToken;

    VariableOperation() {
        this.mLastToken = null;
    }

    Token getLastToken() {
        return mLastToken;
    }

    VariableParser.Error add(ParserAdd parser) {
        VariablePersistence persistence = VariablePersistence.getInstance();
        int col = Integer.parseInt(parser.getValue(Token.COLUMN).getText()) - 1;
        Token val = parser.getValue(Token.VAL);
        mLastToken = val;

        TableColumn.IVariable variable = persistence.getVariable(col);
        VariableParser.Error err = VariableParser.instanceOf(val, variable);

        if (err == VariableParser.Error.MATCH_NUMBER) addNumber(variable, val);
        else if (err == VariableParser.Error.MATCH_TEXT) addString(variable, val);
        else if (err == VariableParser.Error.MATCH_MANY_VALUES) {

            String[] values = val.getText().replaceAll(" ", "").split(",");

        }
        return err;
    }

    private void addNumber(TableColumn.IVariable variable, Token val){
        ((VariableNumber) variable).add(Integer.valueOf(val.getText()));
    }

    private void addString(TableColumn.IVariable variable, Token val){
        ((VariableString) variable).add(val.getText());
    }
}
