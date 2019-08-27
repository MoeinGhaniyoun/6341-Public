package ast;

import interpreter.Env;
import interpreter.Interpreter;

public class ConstExpr extends Expr {

    final Object value;

    public ConstExpr() {
        this.value = Interpreter.NIL;
    }

    public ConstExpr(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    Object eval(Env env) {
        return value;
    }
}
