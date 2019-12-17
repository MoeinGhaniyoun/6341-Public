package ast;

import interpreter.Env;

abstract public class Cond extends ASTNode {

    Cond(Location loc) {
        super(loc);
    }

    abstract boolean eval(Env env);
}
