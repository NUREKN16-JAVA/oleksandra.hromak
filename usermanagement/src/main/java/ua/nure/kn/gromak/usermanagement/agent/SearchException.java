package ua.nure.kn.gromak.usermanagement.agent;

public class SearchException extends Exception {
    public SearchException() {
        super();
    }

    public SearchException(String mes) {
        super(mes);
    }

    public SearchException(String mes, Throwable cause) {
        super(mes, cause);
    }

    public SearchException(Throwable cause) {
        super(cause);
    }

    protected SearchException(String mes, Throwable cause, boolean suppres, boolean stackTrace) {
        super(mes, cause, suppres, stackTrace);
    }
}
