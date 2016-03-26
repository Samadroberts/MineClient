package User32;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;

/**
 * Created by Sam Roberts on 3/25/2016.
 */
public interface User32 extends W32APIOptions {
    User32 instance = (User32) Native.loadLibrary("user32", User32.class,
            DEFAULT_OPTIONS);

    int SW_SHOW = 1;


    boolean ShowWindow(WinDef.HWND hWnd, int nCmdShow);

    boolean SetForegroundWindow(WinDef.HWND hWnd);

    boolean SetFocus(WinDef.HWND hwnd);

    boolean BringWindowToTop(WinDef.HWND hwnd);

    boolean GetWindowRect(WinDef.HWND hwnd, WinDef.RECT rect);

    boolean SetWindowPos(WinDef.HWND hwnd, int hwndAfter, int x, int y, int cx ,int cy, int flags);

    WinDef.HWND FindWindow(String winClass, String title);
}
