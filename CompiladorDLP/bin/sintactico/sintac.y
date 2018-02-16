// @author Raúl Izquierdo

/* No es necesario modificar esta sección ------------------ */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

/* Precedencias aquí --------------------------------------- */

%left 'OR'
%left 'AND' 
%left '>' '<' 'IGUALMENORQUE' 'IGUALMAYORQUE' 'EQUAL' 'DISTINT'
%left '+' '-'
%left '*' '/'
%left '.'


%nonassoc MENORQUETIPO
%nonassoc MAYORQUETIPO
%%

/* Añadir las reglas en esta sección ----------------------- */

programa: 
	|programa definiciones
	;

definiciones:definicionStruct
	|definicionFuncion
	|definicionVariable
	;
	
definicionVariable:'VAR' 'IDENT' ':' tipo ';'
	;

tipo:'CHAR' | 'REAL' | 'INT' | 'IDENT' | '[' 'LITINT' ']' tipo 
	;

definicionStruct: 'STRUCT' 'IDENT' '{' atributos '}' ';'
	;
	
atributos:
	|atributos atributo
	;
	
atributo:'IDENT' ':' tipo ';'
	;
	
definicionFuncion: cabecera '{' cuerpo '}'
	;

cabecera: 'IDENT' '(' parametrosDeclaracion ')' %prec MENORQUETIPO
	| 'IDENT' '(' parametrosDeclaracion ')' ':' tipo
		;

parametrosDeclaracion:
	|listaDeclaracion
	;
listaDeclaracion:parametroDeclaracion
	|listaDeclaracion ',' parametroDeclaracion 
	; 
	
parametroDeclaracion:'IDENT' ':' tipo 
	;

cuerpo:declaracionVariable sentencias
	;
	
declaracionVariable:
	|declaracionVariable dec
	;
dec:'VAR' 'IDENT' ':' tipo ';';


sentencias:
	|sentencias sentencia
	;

sentencia:'PRINT' expr ';'
	|'READ' expr ';'
	|asignacion
	|llamadaFuncion ';'
	|return 
	|'IF' '(' expr ')' '{' sentencias '}' 
	|'IF' '(' expr ')' '{' sentencias '}' 'ELSE' '{' sentencias '}'
	|'WHILE' '(' expr ')' '{' sentencias '}' 
	;
	
return:'RETURN' parametrosLlamada ';'

expr:expr 'EQUAL' expr
	|expr 'DISTINT' expr
	|expr '<' expr
	|expr 'IGUALMENORQUE' expr
	|expr '>' expr
	|expr 'IGUALMAYORQUE' expr
	|expr 'AND' expr
	|expr 'OR' expr
	|expr '+' expr
	|expr '-' expr
	|expr '*' expr
	|expr '/' expr
	|'(' expr ')'
	|'CAST' '<' tipo '>' '(' expr ')'
	|expr'.'expr
	|'LITINT'
	|'LITREAL'
	|'LITCHAR'
	|'IDENT'
	|accesoArray
	|llamadaFuncion
	;
	
accesoArray:'IDENT' '[' expr ']' recursividadArray
	;
	
recursividadArray:
	|'[' expr ']' recursividadArray
	;

asignacion:parteIzquierda '=' parteDerecha ';'
	;
	
parteIzquierda:'IDENT' recursividadArray
	|parteIzquierda '.' parteIzquierda
	;
	
parteDerecha:expr

llamadaFuncion:'IDENT' '(' parametrosLlamada ')' 
	;
	
parametrosLlamada:
	|listaLlamada
	;
listaLlamada:parametroLlamada
	|listaLlamada ',' parametroLlamada
	;

parametroLlamada:expr
	;
	









%%
/* No es necesario modificar esta sección ------------------ */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

// Métodos de acceso para el main -------------
public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

// Funciones requeridas por Yacc --------------
void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sintáctico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
}

int yylex() {
	try {
		int token = lex.yylex();
		yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
		return token;
	} catch (IOException e) {
		return -1;
	}
}

private Yylex lex;
private GestorErrores gestor;
private AST raiz;
