package ast;

import java.io.PrintStream;

import interpreter.Env;

public class WhileStmt extends Stmt {

    final Cond cond;
    final Stmt stmt;

    public WhileStmt(Cond cond, Stmt stmt, Location loc) {
        super(loc);
        this.cond = cond;
        this.stmt = stmt;
    }

    @Override
    public void println(PrintStream ps, String indent) {
        ps.println(indent + "while (" + cond + ")");
        stmt.println(ps, indent + "  ");
    }

    @Override
    void exec(Env env) {
        while (cond.eval(env)) {
            stmt.exec(env);
        }
    }
}
