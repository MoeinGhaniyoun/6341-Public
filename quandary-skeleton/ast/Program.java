package ast;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.io.PrintStream;

import interpreter.Env;

public class Program extends ASTNode {

    final List<FuncDef> funcDefs;

    public Program(List<FuncDef> funcDefs, Location loc) {
        super(loc);
        this.funcDefs = funcDefs;
    }

    public FuncDef getFuncDef(String ident) {
        for (FuncDef funcDef : funcDefs) {
            if (funcDef.varDecl.ident.equals(ident)) {
                return funcDef;
            }
        }
        return null;
    }

    public void println(PrintStream ps) {
        for (FuncDef funcDef : funcDefs) {
            funcDef.println(ps);
            ps.println();
        }
    }

    public void checkStatically() {
        // TODO: implement
    }

    public Object exec(long argument, Env env) {
        CallExpr call = new CallExpr("main", new LinkedList<Expr>(Arrays.asList(new ConstExpr(argument, null))), null);
        return call.eval(env);
    }
}
