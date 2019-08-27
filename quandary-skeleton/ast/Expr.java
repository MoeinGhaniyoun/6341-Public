package ast;

import interpreter.Env;

public abstract class Expr extends ASTNode {

    abstract Object eval(Env env);
}
