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

ADD             = "add "
ADD_VAR         = "add-var "
DELETE          = "del"
DELETE_VAR      = "del-var"
EDIT            = "edt"
EDIT_VAR        = "edt-var"
COLUMN          = "col"
VAL             = "val"

NUMBER          = [0-9]+
TEXT            = [a-zA-Z_]+
VALUES          = {NUMBER}({DIVIDER}{NUMBER})+

%%

{SPACE}         {break;}
{ADD}           {return token(Token.ADD);}
{ADD_VAR}       {return token(Token.ADD_VAR);}
{DELETE}        {return token(Token.DELETE);}
{DELETE_VAR}    {return token(Token.DELETE_VAR);}
{EDIT}          {return token(Token.EDIT);}
{COLUMN}        {return token(Token.COLUMN);}
{VAL}           {return token(Token.VAL);}
{NUMBER}        {return token(Token.NUMBER);}
{VALUES}        {return token(Token.VALUES);}
{TEXT}          {return token(Token.TEXT);}
{ANY}           {return error();}