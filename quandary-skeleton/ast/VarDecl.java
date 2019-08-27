package ast;

public class VarDecl {

    final boolean isMutable;
    final Type type;
    final String ident;

    public VarDecl(boolean isMutable, Type type, String ident) {
        this.isMutable = isMutable;
        this.type = type;
        this.ident = ident;
    }

    @Override
    public String toString() {
        return (isMutable ? "mutable " : "") + type + " " + ident;
    }
}
