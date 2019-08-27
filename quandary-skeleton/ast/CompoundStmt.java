package ast;

import java.util.List;
import java.io.PrintStream;

import interpreter.Env;

public class CompoundStmt extends Stmt {

    final List<Stmt> stmts;

    public CompoundStmt(List<Stmt> stmts) {
        this.stmts = stmts;
    }

    @Override
    void println(PrintStream ps, String indent) {
        ps.println(indent + "{");
        for (Stmt stmt : stmts) {
            stmt.println(ps, indent + "  ");
        }
        ps.println(indent + "}");
    }

    @Override
    void exec(Env env) {
        for (Stmt stmt : stmts) {
            stmt.exec(env);
        }
    }
}
