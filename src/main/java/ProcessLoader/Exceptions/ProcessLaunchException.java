package ProcessLoader.Exceptions;

/**
 * Created by Sam Roberts on 3/23/2016.
 */
public class ProcessLaunchException extends Exception {

    private static final String EXCEPTION_MSG = "Failed to Start Process";

    public ProcessLaunchException() { super(EXCEPTION_MSG); }
    public ProcessLaunchException(String message) {
        super(message);
    }
    public ProcessLaunchException(String message, Throwable cause) {
        super(message, cause);
    }
    public ProcessLaunchException(Throwable cause) {
        super(cause);
    }
}

