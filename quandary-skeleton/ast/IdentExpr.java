package ast;

import interpreter.Env;

public class IdentExpr extends Expr {

    final String ident;

    public IdentExpr(String ident) {
        this.ident = ident;
    }

    @Override
    public String toString() {
        return ident;
    }

    @Override
    Object eval(Env env) {
        return env.getVarValue(ident);
    }
}
