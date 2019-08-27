package ast;

import java.util.List;
import java.util.Iterator;
import java.io.PrintStream;

import interpreter.Env;
import interpreter.EarlyReturnException;

public class FuncDef extends ASTNode {

    final VarDecl varDecl;
    final List<VarDecl> formalDecls;
    final List<Stmt> stmts;

    public FuncDef(VarDecl varDecl, List<VarDecl> formalDecls, List<Stmt> stmts) {
        this.varDecl = varDecl;
        this.formalDecls = formalDecls;
        this.stmts = stmts;
    }

    void println(PrintStream ps) {
        ps.println(varDecl + "(" + listAsDelimitedString(formalDecls, ", ") + ") {");
        for (Stmt s : stmts) {
            s.println(ps, "  ");
        }
        ps.println("}");
    }

    Object execBody(Env env, List<Object> actuals) {
        Iterator<Object> actualsIter = actuals.iterator();
        for (VarDecl formal : formalDecls) {
            Object actual = actualsIter.next();
            env.putVarValue(formal.ident, actual);
        }
        try {
            for (Stmt stmt : stmts) {
                stmt.exec(env);
            }
        } catch (EarlyReturnException ex) {
            return ex.getReturnValue();
        }
        throw new RuntimeException("Unexpected");
    }
}
