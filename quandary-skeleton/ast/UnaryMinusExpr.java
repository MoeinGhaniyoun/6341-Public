package ast;

import interpreter.Env;

public class UnaryMinusExpr extends Expr {

    final Expr expr;

    public UnaryMinusExpr(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "-(" + expr + ")";
    }

    @Override
    Object eval(Env env) {
        return -(long)expr.eval(env);
    }
}
