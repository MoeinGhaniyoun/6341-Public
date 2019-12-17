package ast;

import interpreter.Env;
import interpreter.CellRef;

public class ConstExpr extends Expr {

    final Object value;

    public ConstExpr(Location loc) {
        super(loc);
        this.value = CellRef.NIL;
    }

    public ConstExpr(long value, Location loc) {
        super(loc);
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
