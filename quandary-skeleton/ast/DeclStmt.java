package ast;

import java.io.PrintStream;

import interpreter.Env;

public class DeclStmt extends Stmt {

    final VarDecl varDecl;
    final Expr expr;

    public DeclStmt(VarDecl varDecl, Expr expr) {
        this.varDecl = varDecl;
        this.expr = expr;
    }

    @Override
    void println(PrintStream ps, String indent) {
        ps.println(indent + varDecl + " = " + expr + ";");
    }

    @Override
    void exec(Env env) {
        Object value = expr.eval(env);
        env.putVarValue(varDecl.ident, value);
    }
}
