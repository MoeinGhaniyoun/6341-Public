package ast;

import java.io.PrintStream;

import interpreter.Env;

public class AssignStmt extends Stmt {

    final String ident;
    final Expr expr;

    public AssignStmt(String ident, Expr expr) {
        this.ident = ident;
        this.expr = expr;
    }

    @Override
    void println(PrintStream ps, String indent) {
        ps.println(indent + ident + " = " + expr + ";");
    }

    @Override
    void exec(Env env) {
        Object value = expr.eval(env);
        env.putVarValue(ident, value);
    }
}
