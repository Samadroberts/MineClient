package User32.Exceptions;

/**
 * Created by Sam Roberts on 3/25/2016.
 */
public enum  WindowExceptions {
    SET_WINDOW_FOCUS_FAILURE("Failed setting focus to this window for window:"),
    SET_FOREGROUND_FAILURE("Failed setting window to foreground for window:"),
    BRING_TO_TOP_FAILURE("Failed bringing window to top for window:"),
    SHOW_WINDOW_FAILURE("Failed showing window:"),
    FIND_WINDOW_FAILURE("Could not find window:");


    private String desc;
    WindowExceptions(String desc) {
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }
}
