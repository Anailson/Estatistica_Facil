package lexer;

import exception.TokenException;

import java.util.ArrayList;

public abstract class SyntacticParser {

    private ArrayList<Token> tokens;
    private int index;

    protected SyntacticParser() {
        this.tokens = new ArrayList<>();
        this.index = 0;
    }

    protected abstract Parser.IBaseOperation parse(Token token) throws TokenException;

    void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    protected final Token eat(int... types) throws TokenException{

        if (hasTokens() && tokensOnTopEqual(types)) {
            return tokens.get(index++);
        }

        int position = tokens.get(index - 1).getEndPosition();
        String msgErr = types.length <= 1 ? "Era esperado um comando" : "Eram esperados um dos comandos:";
        for(int type : types){
            msgErr += " <" + Token.name(type) + ">,";
        }
        msgErr = msgErr.substring(0, msgErr.length() - 1) + ".";
        throw new TokenException(new Token(Token.ANY, "", position), TokenException.CMD_UNEXPECTED_END + " " + msgErr);
    }

    protected boolean hasTokens() {
        return index < tokens.size();
    }

    private boolean tokensOnTopEqual(int[] types) {
        int top = tokens.get(index).getType();

        for (int type : types) {
            if (type == top) {
                return true;
            }
        }
        return false;
    }
}