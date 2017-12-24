package operations;

import lexer.Parser;
import lexer.Token;

public class OperationError implements Parser.IBaseOperation {

    private int id;
    private Token last;
    private Integer[] expected;

    public OperationError(int id, Token actual, Integer[] expected) {
        this.id = id;
        this.last = actual;
        this.expected = expected;
    }

    public String error(){

        String error = "Token esperado: [";
        if (expected.length > 1) {
            error = "Tokens esperados: [";
        }
        /*
        for (Token.Type ex : expected) {
            error += ex.name() + ", ";
        }
        error = error.substring(0, error.length() - 2) + "]. ";
        error += "Token encontrado: [" + last.getType().name() + "]";
        error += " na posicao " + last.getPosition();
        error += ", valor = \'" + last.getText() + "\'";
        */
        return error;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Token getValue(int type) {
        return null;
    }

}
