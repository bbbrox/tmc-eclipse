package fi.helsinki.cs.tmc.core.ui;

public class UserVisibleException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserVisibleException(String msg) {
        super(msg);
    }

    public UserVisibleException(String msg, Throwable cause) {
        super(msg, cause);
    }
}