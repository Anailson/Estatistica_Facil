package com.sharktech.projectprob.parser;

import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;
import com.sharktech.projectprob.persistence.VariablePersistence;

import lexer.Token;
import parser.ParserAdd;
import parser.ParserEdit;
import parser.ParserNew;

class VariableOperation {

    private Token mLastToken;

    VariableOperation() {
        this.mLastToken = null;
    }

    Token getLastToken() {
        return mLastToken;
    }

    VariableParser.Error add(ParserAdd parser) {

        int col = Integer.parseInt(parser.getValue(Token.COLUMN).getText()) - 1;
        Token val = parser.getValue(Token.VAL);
        mLastToken = val;

        TableColumn.IVariable variable = VariablePersistence.getInstance().getVariable(col);
        VariableParser.Error err = type(val, variable);

        if (err == VariableParser.Error.MATCH_NUMBER) addNumber(variable, val.getText());
        else if (err == VariableParser.Error.MATCH_TEXT) addString(variable, val.getText());
        else if (err == VariableParser.Error.MATCH_MANY_VALUES) {

            String[] values = val.getText().replaceAll(" ", "").split(",");
            VariableParser.Error type = type(values, variable);
            if (type == VariableParser.Error.MATCH_NUMBER) addNumber(variable, values);
            else if (type == VariableParser.Error.MATCH_TEXT) addString(variable, values);
            else return VariableParser.Error.ERR_GENERAL;
        }
        return err;
    }

    VariableParser.Error newVar(ParserNew parser){
        String title = parser.getValue(Token.TEXT).getText();
        mLastToken = parser.getValue(Token.VAL);
        String[] values = mLastToken.getText().split(",");
        VariableParser.Error type = type(values);

        TableColumn.IVariable var = null;
        if(type == VariableParser.Error.MATCH_NUMBER) {
            var = new VariableNumber(title);
            addNumber(var, values);
        } else if(type == VariableParser.Error.MATCH_TEXT) {
            var = new VariableString(title);
            addString(var, values);
        }

        if(type != VariableParser.Error.ERR_GENERAL) {
            VariablePersistence.getInstance().add(var);
        }
        return type;
    }

    VariableParser.Error edit(ParserEdit parser){

        VariablePersistence persistence = VariablePersistence.getInstance();

        int col = Integer.parseInt(parser.getValue(Token.COLUMN).getText());
        int row = Integer.parseInt(parser.getValue(Token.ROW).getText());
        Token val = parser.getValue(Token.VAL);
        TableColumn.IVariable variable = persistence.getVariable(col);
        VariableParser.Error error = type(val, variable);

        TableCell.ICell cell = null;

        if(error == VariableParser.Error.MATCH_NUMBER) {
            Integer valNum = Integer.parseInt(val.getText());
            cell = new VariableNumber.ValueInteger(valNum);
        } else if(error == VariableParser.Error.MATCH_TEXT) {
            cell = new VariableString.ValueString(val.getText());
        }

        if(cell != null) {
            variable.setElement(cell, row);
        }
        return error;
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
            return isNumber(values[0])
                    ? VariableParser.Error.MATCH_NUMBER
                    : VariableParser.Error.MATCH_TEXT;
        }
        return VariableParser.Error.ERR_GENERAL;
    }

    private VariableParser.Error type(String[] values, TableColumn.IVariable variable) {
        if (values.length > 0) {
            boolean isNumber = isNumber(values[0]);
            Class cls = variable.getClass();

            if(isNumber && cls == VariableNumber.class) return VariableParser.Error.MATCH_NUMBER;
            else if(!isNumber && cls == VariableString.class) return VariableParser.Error.MATCH_TEXT;
            else if(isNumber && cls != VariableNumber.class) return VariableParser.Error.ERR_NUMBER_TEXT;
            else if(!isNumber && cls != VariableString.class) return VariableParser.Error.ERR_TEXT_NUMBER;
        }
        return VariableParser.Error.ERR_GENERAL;
    }

    private VariableParser.Error type(Token token, TableColumn.IVariable var) {
        int type = token.getType();
        Class cls = var.getClass();

        if (type == Token.NUMBER && cls == VariableNumber.class) return VariableParser.Error.MATCH_NUMBER;
        else if (type == Token.TEXT && cls == VariableString.class) return VariableParser.Error.MATCH_TEXT;
        else if (type == Token.NUMBER && cls != VariableNumber.class) return VariableParser.Error.ERR_NUMBER_TEXT;
        else if (type == Token.TEXT && cls != VariableString.class) return VariableParser.Error.ERR_TEXT_NUMBER;
        else if (type == Token.VALUES) return VariableParser.Error.MATCH_MANY_VALUES;
        return VariableParser.Error.ERR_GENERAL;
    }

    private boolean isNumber(String values){
        return values.matches("[0-9]+");
    }
}
