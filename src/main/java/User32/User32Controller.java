package User32;

import ProcessLoader.ProcessLoader;
import User32.Exceptions.WindowException;
import User32.Exceptions.WindowExceptions;
import com.sun.jna.platform.win32.WinDef;

/**
 * Created by Sam Roberts on 3/25/2016.
 */
public class User32Controller {
    private User32 user32;

    //Used to Set Window to topMost
    private static final int HWND_TOPMOST = -1;
    //Used as Flag to not change the windows POS onscreen
    private static final int SWP_NOMOVE = 0x0002;
    //Used as Flag to not Change the Window Size
    private static final int SWP_NOSIZE = 0x0001;

    public User32Controller() {
        user32 = User32.instance;
    }

    public void setWindowFocus(String windowName) throws WindowException {
        WinDef.HWND hwnd = findWindow(windowName);
        if(!user32.SetFocus(hwnd)) {
            throw new WindowException(WindowExceptions.SET_WINDOW_FOCUS_FAILURE,
                    windowName);
        }
    }

    public void setForeGroundWindow(String windowName) throws WindowException {
        if(!user32.SetForegroundWindow(findWindow(windowName))) {
            throw new WindowException(WindowExceptions.SET_FOREGROUND_FAILURE,
                    windowName);
        }
    }

    public void bringWindowToTop(String windowName) throws WindowException {
        if(!user32.BringWindowToTop(findWindow(windowName))) {
            throw new WindowException(WindowExceptions.BRING_TO_TOP_FAILURE,
                    windowName);
        }
    }

    public boolean setTopMost(String windowName) {
        try {
            user32.SetWindowPos(findWindow(windowName),HWND_TOPMOST, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
        } catch (WindowException e) {
            //System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Set to top!");
        return true;
    }

    public void showWindow(String windowName) throws WindowException {
        if(!user32.ShowWindow(findWindow(windowName),User32.SW_SHOW)) {
            throw new WindowException(WindowExceptions.SHOW_WINDOW_FAILURE,
                    windowName);
        }
    }

    public WinDef.RECT getWindowAsRECT(String windowName) {
        WinDef.RECT rect = new WinDef.RECT();
        try {
            user32.GetWindowRect(findWindow(windowName),rect);
        } catch (WindowException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return rect;
    }

    private WinDef.HWND findWindow(String windowName) throws WindowException {
        WinDef.HWND hwnd = user32.FindWindow(null,windowName);
        if(hwnd == null) {
            throw new WindowException(WindowExceptions.FIND_WINDOW_FAILURE,
                    windowName);
        }
        return hwnd;
    }

}
