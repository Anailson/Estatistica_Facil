package exception;

import lexer.Token;

import java.util.Locale;

public class TokenException extends Exception{


    public static final String CMD_UNKNOWN = "Comando desconhecido.";
    public static final String CMD_EMPTY = "Nenhum comando encontrado.";
    public static final String CMD_UNEXPECTED_INITIAL = "Comando inicial inesperado.";
    public static final String CMD_UNEXPECTED_END = "Comando finalizado incorretamente.";

    private Token unexpected;
    private String error;

    public TokenException(Token unexpected, String error) {
        super(error);
        init(unexpected, error);
    }

    private void init(Token unexpected, String error){
        this.unexpected = unexpected;
        this.error = error;
    }

    public String getText(){
        return unexpected.getText();
    }

    public int position(){
        return unexpected.getPosition();
    }

    public String getTokenInfo(){
        return unexpected.isInvalid()
            ? String.format(Locale.getDefault(), "Posicao '%d'.", unexpected.getPosition())
            : String.format(Locale.getDefault(), "Valor encontrado '%s' na posicao '%d'.",
                    unexpected.getText(), unexpected.getPosition());
    }

    @Override
    public String getMessage() {
        return String.format(Locale.getDefault(), "%s %s", this.error, getTokenInfo());
    }
}