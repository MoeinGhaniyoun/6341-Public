package ast;

import java.io.PrintStream;

import interpreter.Env;

public class CallStmt extends Stmt {

    final CallExpr expr;

    public CallStmt(CallExpr expr) {
        super(expr.loc);
        this.expr = expr;
    }

    @Override
    public void println(PrintStream ps, String indent) {
        ps.println(indent + expr + ";");
    }

    @Override
    void exec(Env env) {
        expr.eval(env);
    }
}
