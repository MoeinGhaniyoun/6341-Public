package interpreter;

import ast.Location;

import static interpreter.MemoryManager.NIL_ADDR;

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
        } else {
            leftWord = ((CellRef)left).addr;
        }
        if (right instanceof Long) {
            typeInfo |= RIGHT_IS_INT;
            rightWord = (long)right;
        } else {
            rightWord = ((CellRef)right).addr;
        }
        heap.store(addr + TYPE_OFFSET, typeInfo);
        heap.store(addr + LEFT_OFFSET, leftWord);
        heap.store(addr + RIGHT_OFFSET, rightWord);
    }

    static Object getLeftContents(long addr, Heap heap, Location loc) {
        return getLeftOrRightContents(addr, LEFT_IS_INT, LEFT_OFFSET, heap, loc);
    }

    static Object getRightContents(long addr, Heap heap, Location loc) {
        return getLeftOrRightContents(addr, RIGHT_IS_INT, RIGHT_OFFSET, heap, loc);
    }

    private static Object getLeftOrRightContents(long addr, long isIntMask, long contentsOffset, Heap heap, Location loc) {
        if (addr == NIL_ADDR) {
            Interpreter.fatalError("Nil dereference at " + loc, Interpreter.EXIT_NIL_REF_ERROR);
        }
        long typeInfo = heap.load(addr + TYPE_OFFSET);
        long contents = heap.load(addr + contentsOffset);
        if ((typeInfo & isIntMask) != 0) {
            return contents;
        } else {
            return new CellRef(contents);
        }
    }   

    static void setLeftContents(long addr, Object value, Heap heap, Location loc) {
        setLeftOrRightContents(addr, LEFT_IS_INT, LEFT_OFFSET, value, heap, loc);
    }

    static void setRightContents(long addr, Object value, Heap heap, Location loc) {
        setLeftOrRightContents(addr, RIGHT_IS_INT, RIGHT_OFFSET, value, heap, loc);
    }

    private static void setLeftOrRightContents(long addr, long isIntMask, long contentsOffset, Object value, Heap heap, Location loc) {
        if (addr == NIL_ADDR) {
            Interpreter.fatalError("Nil dereference at " + loc, Interpreter.EXIT_NIL_REF_ERROR);
        }
        long typeInfo = heap.load(addr + TYPE_OFFSET);
        long newContents;
        if ((typeInfo & isIntMask) != 0) {
            if (!(value instanceof Long)) {
                Interpreter.fatalError("Not allowed to store non-int into int slot (at " + loc + ")", Interpreter.EXIT_DYNAMIC_TYPE_ERROR);
            }
            newContents = (long)value;
        } else {
            if (value instanceof Long) {
                Interpreter.fatalError("Not allowed to store int into non-int slot (at " + loc + ")", Interpreter.EXIT_DYNAMIC_TYPE_ERROR);
            }
            newContents = ((CellRef)value).addr;
        }
        heap.store(addr + contentsOffset, newContents);
    }
}
