package ast;

import interpreter.Env;

public class NotExpr extends Cond {

    final Cond expr;

    public NotExpr(Cond expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "!(" + expr + ")";
    }

    @Override
    boolean eval(Env env) {
        return !expr.eval(env);
    }
}
