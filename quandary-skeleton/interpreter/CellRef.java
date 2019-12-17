package interpreter;

public class CellRef {

    final long addr;

    CellRef(final long addr) {
        this.addr = addr;
    }

    public static final Object NIL = new CellRef(0L);

    @Override
    public boolean equals(Object other) {
        return other instanceof CellRef && this.addr == ((CellRef)other).addr;
    }

    @Override
    public String toString() {
        if (this.equals(NIL)) {
            return "nil";
        }
        return "(" + CellLayout.getLeftContents(addr, Interpreter.getInterpreter().getHeap(), null) + " . " +
                     CellLayout.getRightContents(addr, Interpreter.getInterpreter().getHeap(), null) + ")";
    }
}
