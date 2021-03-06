package lexer;

%%
%public
%class Lexer
%type Token
%column
%function next

%{
    private Token token(int type){
        return new Token(type, yytext(), yycolumn);
    }

    private Token error(){
        return new Token(Token.ANY, yytext(), yycolumn);
    }
%}
ANY             = .
SPACE           = [\ \n\t\r]*
DIVIDER         = "," | {SPACE}"," | ","{SPACE} | {SPACE}","{SPACE}

NEW             = "new"
ADD             = "add"
VAR             = "var"
DELETE          = "del"
EDIT            = "edt"
COLUMN          = "col"
ROW             = "row"
VAL             = "val"

NUMBER          = [0-9]+
LETTER          = [a-zA-Z]
CHARACTER       = [-_]
TEXT            = {LETTER} | {LETTER}({NUMBER} | {LETTER} | {CHARACTER})+
VALUES          = {TEXT}({DIVIDER}{TEXT})+ | {NUMBER}({DIVIDER}{NUMBER})+

%%

{SPACE}         {break;}
{NEW}           {return token(Token.NEW);}
{ADD}           {return token(Token.ADD);}
{VAR}           {return token(Token.VAR);}
{DELETE}        {return token(Token.DELETE);}
{EDIT}          {return token(Token.EDIT);}
{COLUMN}        {return token(Token.COLUMN);}
{ROW}           {return token(Token.ROW);}
{VAL}           {return token(Token.VAL);}
{NUMBER}        {return token(Token.NUMBER);}
{VALUES}        {return token(Token.VALUES);}
{TEXT}          {return token(Token.TEXT);}
{ANY}           {return error();}