package MouseTimerTaskEvents;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

/**
 * Created by Sam Roberts on 3/30/2016.
 */
public class MouseClickChecker extends TimerTask {

    private final Robot robot;
    private static final int leftArrow = KeyEvent.VK_LEFT;

    public MouseClickChecker(Robot robot) {
        this.robot = robot;
    }

    public void run() {
        robot.keyPress(leftArrow);
    }

    public void stop() {
        robot.keyRelease(leftArrow);
    }
}
