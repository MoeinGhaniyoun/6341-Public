package interpreter;

abstract class MemoryManager {

    static final int WORDS_IN_CELL = 3;
    static final int BYTES_IN_CELL = Heap.BYTES_IN_WORD * WORDS_IN_CELL;

    static final long INVALID_ADDR = Long.MAX_VALUE;
    static final long MM_WORD_OFFSET = 0; // first word of a cell has MM bits
    static final long MM_BITS = 32; // lower half of word is for MM, upper half is for type info

    final Heap heap;

    MemoryManager(Heap heap) {
        this.heap = heap;
    }

    abstract long alloc(Env env);

    // Required value of the MM word have for newly allocated objects
    long initialMMWord() {
        return 0;
    }

    // Returns true iff free performed successfully
    boolean free(long addr) {
        return false;
    }

    // Return true iff GC (presumably stop-the-world trace-based GC) performed successfully
    boolean gc(Env env) {
        return false;
    }
}
