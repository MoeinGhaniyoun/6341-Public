package ast;

import interpreter.Env;

public class LogicalCond extends Cond {

    public static final int AND = 1;
    public static final int OR = 2;

    final Cond expr1;
    final int operator;
    final Cond expr2;

    public LogicalCond(Cond expr1, int operator, Cond expr2, Location loc) {
        super(loc);
        this.expr1 = expr1;
        this.operator = operator;
        this.expr2 = expr2;
    }

    @Override
    public String toString() {
        return "(" + expr1 + (operator == AND ? " && " : " || ") + expr2 + ")";
    }

    @Override
    boolean eval(Env env) {
        switch (operator) {
            case AND: return expr1.eval(env) && expr2.eval(env);
            case OR:  return expr1.eval(env) || expr2.eval(env);
        }
        throw new RuntimeException("Unreachable in LogicalCond.eval");
    }
}
