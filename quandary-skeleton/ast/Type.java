package ast;

public enum Type {

    INT, REF, QTYPE;

    @Override
    public String toString() {
        switch (this) {
            case INT:   return "int";
            case REF:  return "Ref";
            case QTYPE: return "Q";
        }
        throw new RuntimeException("Unreachable in Type.toString");
    }
}
