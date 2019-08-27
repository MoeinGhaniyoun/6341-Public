package ast;

public enum Type {

    INT, CELL, QTYPE;

    @Override
    public String toString() {
        switch (this) {
            case INT:   return "int";
            case CELL:  return "Cell";
            case QTYPE: return "Q";
        }
        throw new RuntimeException("Unreachable");
    }
}
