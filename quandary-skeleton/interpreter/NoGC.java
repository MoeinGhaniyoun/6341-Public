package interpreter;

class NoGC extends MemoryManager {

    long nextAlloc;
    final Object allocLock = new Object();

    NoGC(Heap heap) {
        super(heap);
        nextAlloc = heap.getStartAddr();
    }

    @Override
    long alloc(Env env) {
        synchronized (allocLock) {
            long newAddr = nextAlloc;
            if (newAddr + BYTES_IN_CELL > heap.getEndAddr()) {
                Interpreter.fatalError("Quandary heap ran out of memory", Interpreter.EXIT_QUANDARY_HEAP_OUT_OF_MEMORY_ERROR);
            }
            nextAlloc += BYTES_IN_CELL;
            return newAddr;
        }
    }
}
