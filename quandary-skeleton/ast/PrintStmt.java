package ast;

import java.io.PrintStream;

import interpreter.Env;

public class PrintStmt extends Stmt {

    final Expr expr;

    public PrintStmt(Expr expr, Location loc) {
        super(loc);
        this.expr = expr;
    }

    @Override
    public void println(PrintStream ps, String indent) {
        ps.println(indent + "print " + expr + ";");
    }

    @Override
    void exec(Env env) {
        Object value = expr.eval(env);
        System.out.println(value);
    }
}
