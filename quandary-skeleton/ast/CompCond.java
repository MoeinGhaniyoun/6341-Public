package ast;

import interpreter.Env;

public class CompCond extends Cond {

    public static final int LE = 1;
    public static final int GE = 2;
    public static final int EQ = 3;
    public static final int NE = 4;
    public static final int LT = 5;
    public static final int GT = 6;

    final Expr expr1;
    final int operator;
    final Expr expr2;

    public CompCond(Expr expr1, int operator, Expr expr2) {
        this.expr1 = expr1;
        this.operator = operator;
        this.expr2 = expr2;
    }

    public String toString() {
        String s = null;
        switch (operator) {
            case LE: s = "<="; break;
            case GE: s = ">="; break;
            case EQ: s = "=="; break;
            case NE: s = "!="; break;
            case LT: s = "<";  break;
            case GT: s = ">";  break;
        }
        return "(" + expr1 + " " + s + " " + expr2 + ")";
    }

    @Override
    boolean eval(Env env) {
        switch (operator) {
            case LE: return (long)expr1.eval(env) <= (long)expr2.eval(env);
            case GE: return (long)expr1.eval(env) >= (long)expr2.eval(env);
            case EQ: return (long)expr1.eval(env) == (long)expr2.eval(env);
            case NE: return (long)expr1.eval(env) != (long)expr2.eval(env);
            case LT: return (long)expr1.eval(env) <  (long)expr2.eval(env);
            case GT: return (long)expr1.eval(env) >  (long)expr2.eval(env);
        }
        throw new RuntimeException("Unreachable");
    }
}
