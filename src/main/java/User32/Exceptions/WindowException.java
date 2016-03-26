package User32.Exceptions;

/**
 * Created by Sam Roberts on 3/25/2016.
 */
public class WindowException extends Exception {

    public WindowException(WindowExceptions windowException , String windowName) {
        super(windowException.getDesc() + " " + windowName);
    }


}
