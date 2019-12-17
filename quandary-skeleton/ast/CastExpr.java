package ast;

import interpreter.Env;

public class CastExpr extends Expr {

    final Type type;
    final Expr expr;

    public CastExpr(Type type, Expr expr, Location loc) {
        super(loc);
        this.type = type;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "(" + type + ")" + "(" + expr + ")";
    }

    @Override
    Object eval(Env env) {
        Object value = expr.eval(env);
        // TODO: perform dynamic type check
        /*if (???) {
            Interpreter.fatalError("Dynamic cast to " + type + " for value " + value + " failed", Interpreter.EXIT_DYNAMIC_TYPE_ERROR);
        }*/
        return value;
    }
}
