package ast;

import interpreter.Env;

public class IdentExpr extends Expr {

    final String ident;

    public IdentExpr(String ident, Location loc) {
        super(loc);
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
