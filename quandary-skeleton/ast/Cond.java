package ast;

import interpreter.Env;

abstract public class Cond extends ASTNode {

    abstract boolean eval(Env env);
}
