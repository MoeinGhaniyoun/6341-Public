package ast;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

import interpreter.Interpreter;
import interpreter.Env;

public class CallExpr extends Expr {

    final String ident;
    final List<Expr> exprs;

    public CallExpr(String ident, List<Expr> exprs) {
        this.ident = ident;
        this.exprs = exprs;
    }

    @Override
    public String toString() {
        return ident + "(" + listAsDelimitedString(exprs, ", ") + ")";
    }

    @Override
    Object eval(Env env) {
        LinkedList<Object> actuals = new LinkedList<Object>();
        for (Expr expr : exprs) {
            Object actual = expr.eval(env);
            actuals.add(actual);
        }

        Object returnValue;
        FuncDef funcDef = Interpreter.getInterpreter().getFuncDef(ident);
        if (funcDef != null) {
            // TODO: don't just use the same env?
            returnValue = funcDef.execBody(env, actuals);
        } else {
            returnValue = Interpreter.getInterpreter().callBuiltInFunction(ident, actuals, env);
        }
        if (returnValue == null) {
            // Static checking should make this error impossible
            throw new RuntimeException("Function " + ident + " not found");
        }

        return returnValue;
    }
}
