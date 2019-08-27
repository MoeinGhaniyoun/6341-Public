package interpreter;

import static interpreter.Interpreter.NIL;

class CellLayout {

    static final long TYPE_OFFSET = MemoryManager.MM_WORD_OFFSET;
    static final long LEFT_IS_INT   = 1L << MemoryManager.MM_BITS; // lower half of word is for MM, upper half is for type info
    static final long RIGHT_IS_INT  = LEFT_IS_INT << 1;
    static final long IS_LOCKED = RIGHT_IS_INT << 1;
    static final long LEFT_OFFSET = TYPE_OFFSET + Heap.BYTES_IN_WORD;
    static final long RIGHT_OFFSET = LEFT_OFFSET + Heap.BYTES_IN_WORD;

    static void initializeCell(long addr, long mmInfo, Object left, Object right, Heap heap) {
        long typeInfo = mmInfo;
        long leftWord;
        long rightWord;
        if (left instanceof Long) {
            typeInfo |= LEFT_IS_INT;
            leftWord = (long)left;
        } else if (left instanceof CellRef) {
            leftWord = ((CellRef)left).addr;
        } else { // NIL
            leftWord = MemoryManager.INVALID_ADDR;
        }
        if (right instanceof Long) {
            typeInfo |= RIGHT_IS_INT;
            rightWord = (long)right;
        } else if (right instanceof CellRef) {
            rightWord = ((CellRef)right).addr;
        } else { // NIL
            rightWord = MemoryManager.INVALID_ADDR;
        }
        heap.store(addr + TYPE_OFFSET, typeInfo);
        heap.store(addr + LEFT_OFFSET, leftWord);
        heap.store(addr + RIGHT_OFFSET, rightWord);
    }

    static Object getLeftContents(long addr, Heap heap) {
        return getLeftOrRightContents(addr, LEFT_IS_INT, LEFT_OFFSET, heap);
    }

    static Object getRightContents(long addr, Heap heap) {
        return getLeftOrRightContents(addr, RIGHT_IS_INT, RIGHT_OFFSET, heap);
    }

    private static Object getLeftOrRightContents(long addr, long isIntMask, long contentsOffset, Heap heap) {
        long typeInfo = heap.load(addr + TYPE_OFFSET);
        long contents = heap.load(addr + contentsOffset);
        if ((typeInfo & isIntMask) != 0) {
            return contents;
        } else if (contents == MemoryManager.INVALID_ADDR) {
            return NIL;
        } else {
            return new CellRef(contents);
        }
    }   

    static void setLeftContents(long addr, Object value, Heap heap) {
        setLeftOrRightContents(addr, LEFT_IS_INT, LEFT_OFFSET, value, heap);
    }

    static void setRightContents(long addr, Object value, Heap heap) {
        setLeftOrRightContents(addr, RIGHT_IS_INT, RIGHT_OFFSET, value, heap);
    }

    private static void setLeftOrRightContents(long addr, long isIntMask, long contentsOffset, Object value, Heap heap) {
        long typeInfo = heap.load(addr + TYPE_OFFSET);
        long newContents;
        if ((typeInfo & isIntMask) != 0) {
            if (!(value instanceof Long)) {
                Interpreter.fatalError("Not allowed to store non-int into int slot", Interpreter.EXIT_DYNAMIC_TYPE_ERROR);
            }
            newContents = (long)value;
        } else {
            if (value instanceof Long) {
                Interpreter.fatalError("Not allowed to store int into non-int slot", Interpreter.EXIT_DYNAMIC_TYPE_ERROR);
            }
            if (value instanceof CellRef) {
                newContents = ((CellRef)value).addr;
            } else { // NIL
                newContents = MemoryManager.INVALID_ADDR;
            }
        }
        heap.store(addr + contentsOffset, newContents);
    }
}
