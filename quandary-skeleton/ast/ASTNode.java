package ast;

import java.util.List;

abstract class ASTNode {

    // Helps with printing
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
