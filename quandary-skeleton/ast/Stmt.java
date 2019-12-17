package ast;

import java.io.PrintStream;

import interpreter.Env;

abstract public class Stmt extends ASTNode {

    Stmt(Location loc) {
        super(loc);
    }

    abstract void println(PrintStream ps, String indent);

    abstract void exec(Env env);
}
