package ast;

import java.io.PrintStream;

import interpreter.Env;

public class IfStmt extends Stmt {

    final Cond cond;
    final Stmt thenStmt;
    final Stmt elseStmt;

    public IfStmt(Cond cond, Stmt thenStmt, Location loc) {
        this(cond, thenStmt, null, loc);
    }

    public IfStmt(Cond cond, Stmt thenStmt, Stmt elseStmt, Location loc) {
        super(loc);
        this.cond = cond;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public void println(PrintStream ps, String indent) {
        ps.println(indent + "if (" + cond + ")");
        thenStmt.println(ps, indent + "  ");
        if (elseStmt != null) {
            ps.println(indent + "else");
            elseStmt.println(ps, indent + "  ");
        }
    }

    @Override
    void exec(Env env) {
        boolean result = cond.eval(env);
        if (result) {
            thenStmt.exec(env);
        } else if (elseStmt != null) {
            elseStmt.exec(env);
        }
    }
}
