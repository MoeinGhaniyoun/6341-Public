package ast;

import java.io.PrintStream;

import interpreter.Env;
import interpreter.EarlyReturnException;

public class ReturnStmt extends Stmt {

    final Expr expr;

    public ReturnStmt(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    @Override
    public void println(PrintStream ps, String indent) {
        ps.println(indent + "return " + expr + ";");
    }

    @Override
    void exec(Env env) {
        Object value = expr.eval(env);
        throw new EarlyReturnException(value);
    }
}
