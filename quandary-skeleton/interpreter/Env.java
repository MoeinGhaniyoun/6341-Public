package interpreter;

import java.util.Map;
import java.io.PrintStream;
import java.util.HashMap;

public class Env {

    // TODO: fix environment so it maintains local variable values correctly

    final Map<String,Object> varValueMap;

    public Env() {
        this.varValueMap = new HashMap<String,Object>();
    }

    public Object getVarValue(String ident) {
        return varValueMap.get(ident);
    }

    public void putVarValue(String ident, Object newValue) {
        varValueMap.put(ident, newValue);
    }

    void println(PrintStream ps) {
        for (String ident : varValueMap.keySet()) {
            ps.println(ident + " = " + varValueMap.get(ident));
        }
    }
}
