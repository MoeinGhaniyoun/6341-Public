package ast;

public class VarDecl extends ASTNode {

    final boolean isMutable;
    final Type type;
    final String ident;

    public VarDecl(boolean isMutable, Type type, String ident, Location loc) {
        super(loc);
        this.isMutable = isMutable;
        this.type = type;
        this.ident = ident;
    }

    @Override
    public String toString() {
        return (isMutable ? "mutable " : "") + type + " " + ident;
    }
}
