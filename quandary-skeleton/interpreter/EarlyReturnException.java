package interpreter;

@SuppressWarnings("serial")
public class EarlyReturnException extends RuntimeException {

    final Object returnValue;

    public EarlyReturnException(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Object getReturnValue() {
        return returnValue;
    }
}
