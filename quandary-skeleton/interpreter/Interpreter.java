package interpreter;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

import parser.ParserWrapper;
import ast.Program;
import ast.FuncDef;
import ast.Location;

public class Interpreter {

    // Process return codes
    public static final int EXIT_SUCCESS = 0;
    public static final int EXIT_PARSING_ERROR = 1;
    public static final int EXIT_STATIC_CHECKING_ERROR = 2;
    public static final int EXIT_DYNAMIC_TYPE_ERROR = 3;
    public static final int EXIT_NIL_REF_ERROR = 4;
    public static final int EXIT_QUANDARY_HEAP_OUT_OF_MEMORY_ERROR = 5;

    static private Interpreter interpreter;

    public static Interpreter getInterpreter() {
        return interpreter;
    }

    public static void main(String[] args) {
        String gcType = "NoGC"; // default for skeleton, which only supports NoGC
        long heapBytes = 1 << 14;
        int i = 0;
        String filename;
        long quandaryArg;
        try {
            for (; i < args.length; i++) {
                String arg = args[i];
                if (arg.startsWith("-")) {
                    if (arg.equals("-gc")) {
                        gcType = args[i + 1];
                        i++;
                    } else if (arg.equals("-heapsize")) {
                        heapBytes = Long.valueOf(args[i + 1]);
                        i++;
                    } else {
                        throw new RuntimeException("Unexpected option");
                    }
                } else {
                    if (i != args.length - 2) {
                        throw new RuntimeException("Unexpected number of arguments");
                    }
                    break;
                }
            }
            filename = args[i];
            quandaryArg = Long.valueOf(args[i + 1]);
        } catch (Exception ex) {
            System.out.println("Expected format: quandary [OPTIONS] QUANDARY_PROGRAM_FILE INTEGER_ARGUMENT");
            System.out.println("Options:");
            System.out.println("  -gc (MarkSweep|Explicit|NoGC)");
            System.out.println("  -heapsize BYTES");
            System.out.println("BYTES must be a multiple of the word size (8)");
            return;
        }

        Program astRoot = null;
        Reader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            // TODO: the lexer and parser (and interpreter) don't support concurrent binary expressions, i.e., [ expr1 OP expr2 ]
            astRoot = ParserWrapper.parse(reader);
        } catch (Exception ex) {
            Interpreter.fatalError("Uncaught parsing error: " + ex, Interpreter.EXIT_PARSING_ERROR);
        }
        astRoot.checkStatically();
        interpreter = new Interpreter(astRoot);
        //interpreter.printProgram(); // for debugging
        Object returnValue = interpreter.exec(gcType, heapBytes, quandaryArg);
        System.out.println("Interpreter returned " + returnValue);
    }

    final Program astRoot;
    Heap heap;
    MemoryManager memoryManager;
    final Random random;

    private Interpreter(Program astRoot) {
        this.astRoot = astRoot;
        this.random = new Random();
    }

    void printProgram() {
        astRoot.println(System.out);
    }

    public FuncDef getFuncDef(String ident) {
        return astRoot.getFuncDef(ident);
    }

    Heap getHeap() {
        return heap;
    }

    MemoryManager getMemoryManager() {
        return memoryManager;
    }

    Object exec(String gcType, long heapBytes, long arg) {
        heap = new Heap(0x12345000, heapBytes);
        // TODO: implement memory managers besides NoGC (note that we probably won't implement RefCount in this class)
        if (gcType.equals("Explicit")) {
            throw new RuntimeException("Explicit not yet implemented");            
            //memoryManager = new ExplicitMemoryManager(heap);
        } else if (gcType.equals("MarkSweep")) {
            throw new RuntimeException("MarkSweep not yet implemented");            
            //memoryManager = new MarkSweep(heap);
        } else if (gcType.equals("RefCount")) {
            throw new RuntimeException("RefCount not yet implemented");            
            //memoryManager = new RefCount(heap);
        } else if (gcType.equals("NoGC")) {
            memoryManager = new NoGC(heap);
        }
        Env env = new Env();
        Object returnValue = astRoot.exec(arg, env);
        memoryManager.gc(env); // just for fun
        return returnValue;
    }

    public CellRef cons(Object left, Object right, Env env) {
        long newAddr = memoryManager.alloc(env);
        CellLayout.initializeCell(newAddr, memoryManager.initialMMWord(), left, right, heap);
        CellRef returnValue = new CellRef(newAddr);
        return returnValue;
    }

    public Object callBuiltInFunction(String ident, List<Object> actuals, Env env, Location loc) {
        Iterator<Object> actualsIter = actuals.iterator();
        if (ident.equals("left")) {
            CellRef cellRef = (CellRef)actualsIter.next();
            return CellLayout.getLeftContents(cellRef.addr, heap, loc);
        } else if (ident.equals("right")) {
            CellRef cellRef = (CellRef)actualsIter.next();
            return CellLayout.getRightContents(cellRef.addr, heap, loc);
        } else if (ident.equals("isAtom")) {
            Object value = actualsIter.next();
            return value instanceof CellRef && !value.equals(CellRef.NIL) ? 0L : 1L;
        } else if (ident.equals("isNil")) {
            return actualsIter.next().equals(CellRef.NIL) ? 1L : 0L;
        } else if (ident.equals("setLeft")) {
            CellRef cellRef = (CellRef)actualsIter.next();
            Object newValue = actualsIter.next();
            //Object oldValue = CellLayout.getLeftContents(cellRef.addr, heap);
            CellLayout.setLeftContents(cellRef.addr, newValue, heap, loc);
            return 1L;
        } else if (ident.equals("setRight")) {
            CellRef cellRef = (CellRef)actualsIter.next();
            Object newValue = actualsIter.next();
            //Object oldValue = CellLayout.getRightContents(cellRef.addr, heap);
            CellLayout.setRightContents(cellRef.addr, newValue, heap, loc);
            return 1L;
        } else if (ident.equals("acq")) {
            // TODO: implement
            throw new RuntimeException("acq not yet implemented");
        } else if (ident.equals("rel")) {
            // TODO: implement
            throw new RuntimeException("rel not yet implemented");
        } else if (ident.equals("randomInt")) {
            long formal = (long)actualsIter.next();
            long value = random.nextLong() % formal;
            if (value < 0) value += formal;
            return value;
        } else {
            // TODO: free() isn't supported or implemented
            return null;
        }
    }

	public static void fatalError(String message, int processReturnCode) {
        System.out.println(message);
        System.exit(processReturnCode);
	}
}
