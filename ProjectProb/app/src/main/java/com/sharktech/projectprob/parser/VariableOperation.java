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

        if (err == VariableParser.Error.MATCH_NUMBER) addNumber(variable, val.getText());
        else if (err == VariableParser.Error.MATCH_TEXT) addString(variable, val.getText());
        else if (err == VariableParser.Error.MATCH_MANY_VALUES) {

            String[] values = val.getText().replaceAll(" ", "").split(",");
            VariableParser.Error type = type(values);
            if (type == VariableParser.Error.MATCH_NUMBER) addNumber(variable, values);
            else if (type == VariableParser.Error.MATCH_TEXT) addString(variable, values);
            else return VariableParser.Error.ERR_GENERAL;
        }
        return err;
    }

    private void addNumber(TableColumn.IVariable variable, String value) {
        ((VariableNumber) variable).add(Integer.valueOf(value));
    }

    private void addNumber(TableColumn.IVariable variable, String[] values) {
        Number[] ints = new Number[values.length];
        int index = 0;
        for (String s : values) {
            ints[index++] = Integer.parseInt(s);
        }
        ((VariableNumber) variable).add(ints);
    }

    private void addString(TableColumn.IVariable variable, String value) {
        ((VariableString) variable).add(value);
    }

    private void addString(TableColumn.IVariable variable, String[] values) {
        ((VariableString) variable).add(values);
    }

    private VariableParser.Error type(String[] values) {
        if (values.length > 0) {

            return values[0].matches("[0-9]+")
                    ? VariableParser.Error.MATCH_NUMBER
                    : VariableParser.Error.MATCH_TEXT;
        }
        return VariableParser.Error.ERR_GENERAL;
    }
}
