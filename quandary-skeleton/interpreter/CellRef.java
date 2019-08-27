package interpreter;

public class CellRef {

    final long addr;

    CellRef(final long addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "(" + CellLayout.getLeftContents(addr, Interpreter.getInterpreter().getHeap()) + " . " +
                     CellLayout.getRightContents(addr, Interpreter.getInterpreter().getHeap()) + ")";
    }
}
