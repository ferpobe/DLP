package ast;

import visitor.*;

/*
 *  Esta clase se completará en la fase de Análisis Sintáctico
 */
public interface AST {
	public Object accept(Visitor visitor, Object param);
}

