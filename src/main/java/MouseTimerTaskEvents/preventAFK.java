package MouseTimerTaskEvents;

import ColorSearch.ColorSearch;
import User32.User32Controller;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.TimerTask;

/**
 * Created by Sam Roberts on 3/30/2016.
 */
public class preventAFK extends TimerTask {
    private static final String WINDOW_NAME = "Old School RuneScape";
    private final Robot robot;
    private WinDef.RECT window = new User32Controller().getWindowAsRECT(WINDOW_NAME);

    private boolean clickMouse = true;

    public preventAFK(Robot robot) {
        this.robot = robot;
    }

    public void run() {
        window = new User32Controller().getWindowAsRECT(WINDOW_NAME);
        if (clickMouse) {
            int mid_x = (int) window.toRectangle().getMinX() + ((int) window.toRectangle().getWidth() / 2);
            int mid_y = (int) window.toRectangle().getMinY() + ((int) window.toRectangle().getHeight() / 2);
            robot.mouseMove(mid_x, mid_y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else {
            toggleClickMouseTrue();
        }
    }

    public void toggleClickMouseTrue() {
        clickMouse = true;
    }

    public void toggleClickMouseFalse() {
        clickMouse = false;
    }
}
