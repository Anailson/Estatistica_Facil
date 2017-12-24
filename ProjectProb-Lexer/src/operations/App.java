package operations;

import exception.TokenException;
import jflex.Main;
import lexer.Parser;
import lexer.Token;

import java.io.File;

public class App {


    private static final String ROOT = new File("").getAbsolutePath();
    private static final String PATH = "/src/lexer";
    private static final String LEXER_FILE = "/Lexer.flex";

    private static void generateLexer(){
        File file = new File(ROOT + PATH + LEXER_FILE);
        File lexer = new File(file.getParentFile(), "Lexer.java");
        if(lexer.exists()){
            lexer.delete();
            System.out.println("Lexer.delete()");
        }
        Main.generate(file);
    }

    public static void main(String[] args){
        //App.generateLexer();

        String source;
        source = "add edt del inc row col val";
        source = "3543 sdf ds34 23gf ] edt ][]*%)%¨%";

        source = "edt 34 col 1 row 1" ; //edt <text | number> col <number> row <number>
        source = "del col 1 row 1"; //del col <number> row <number>
        source = "pls 2 col 4 row 5"; //pls <number> col <number> row <number>
        source = "add col"; //add col <number> val <text | number*>

        Parser parser = new Parser();
        parser.register(Token.ADD, new ParserAdd());

        try {
            Parser.IBaseOperation op = parser.analyse(source);

            switch (op.id()){
                case Token.ADD: add((ParserAdd) op); break;
            }
        } catch (TokenException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getTokenInfo());
        }
    }

    private static void add(ParserAdd parser){

        System.out.println(parser.getValue(Token.COLUMN) + " " + parser.getValue(Token.VAL));
    }
}


/*
add col <number> val <text | number*>
{ADD}{COLUMN}{NUMBER}{PARAMETER}

-del col <number> row <number>
{DELETE}{COLUMN}{NUMBER}{ROW}{NUMBER}

-pls <number> col <number> row <number>
{PLUS}{NUMBER}{COLUMN}{NUMBER}{ROW}{NUMBER}

-edt <text | number> col <number> row <number>
{EDIT}{PARAMETER}{COLUMN}{NUMBER}{ROW}{NUMBER}
*/