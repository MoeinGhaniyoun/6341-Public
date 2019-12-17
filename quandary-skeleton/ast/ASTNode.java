package ast;

import java.util.List;

abstract class ASTNode {

    final Location loc;

    ASTNode(Location loc) {
        this.loc = loc;
    }

    // Helper function
    static<T> String listAsDelimitedString(List<T> list, String delim) {
        StringBuffer buf = new StringBuffer();
        String d = "";
        for (T o : list) {
            buf.append(d);
            buf.append(o);
            d = delim;
        }
        return buf.toString();
    }
}
