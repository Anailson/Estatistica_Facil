package lexer;

import java.util.Formatter;

public class Token {

    public static final int ANY = -1;
    public static final int EMPTY = -2;

    public static final int ADD = 2;
    public static final int ADD_VAR = 3;
    public static final int DELETE = 4;
    public static final int DELETE_VAR = 5;
    public static final int EDIT = 6;
    public static final int COLUMN = 8;
    public static final int VAL = 10;
    public static final int NUMBER = 11;
    public static final int TEXT = 12;
    public static final int VALUES = 13;

    private int pos;
    private Type type;

    public Token(int type, String text, int pos){
        this.pos = pos;
        this.type = new Type(type, text.replaceAll(" ", ""));
    }

    public String getText() {
        return type.text;
    }

    public int getPosition(){
        return pos;
    }

    public int getEndPosition(){
        return pos + getText().length();
    }

    public String toString(){
        return new Formatter()
            .format("[%s] --> [%s]", type.tag, type.text)
            .toString();
    }

    public boolean isInvalid(){
        return type.cod == ANY || type.cod == EMPTY;
    }

    public int getType() {
        return type.cod;
    }

    private static class Type{

        private int cod;
        private String tag, text;

        Type(int cod, String text) {
            this.cod = cod;
            this.text = text;
            this.tag = Token.name(cod);
        }
    }

    public static String name(int type){

        switch (type){
            case ADD: return "ADD";
            case ADD_VAR: return "ADD_VAR";
            case DELETE: return "DELETE";
            case DELETE_VAR: return "DELETE_VAR";
            case EDIT: return "EDIT";
            case COLUMN: return "COLUMN";
            case VAL: return "VAL";
            case NUMBER: return "NUMBER";
            case TEXT: return "TEXT";
            case VALUES: return "VALUES";
            default: return "undefined";
        }
    }
}