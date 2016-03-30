package ColorSearch;

import IntegerPoint.IntegerPoint;
import MouseTimerTaskEvents.MouseClickChecker;
import MouseTimerTaskEvents.preventAFK;
import User32.User32Controller;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Timer;

/**
 * Created by Sam Roberts on 3/25/2016.
 */
public class ColorSearch {
    private User32Controller user32Controller;
    private Robot robot;
    private WinDef.RECT rect;
    private String windowName;
    private final Timer mouseTimer;
    private static final long PIXELS_PER_SQUARE = 100;
    private static final long TIME_TO_TRAVERSE_ONE_SQAURE_MS = 600;
    private static final int INVENTORY_WINDOWED_SIZE_X_Y = 100;
    private static final int INVENTORY_FULLSCREEN_SIZE_X_Y = 300;
    private final MouseClickChecker mouseClickChecker;
    private final preventAFK preventAFKTimerTask;
    private static final long SERVER_LATENCY = 500;
    private static final long SEC_IN_MS = 1000;

    public ColorSearch(String windowName) {
        user32Controller = new User32Controller();
        this.windowName = windowName;
        this.rect = user32Controller.getWindowAsRECT(windowName);
        try {
            robot = new Robot();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        mouseTimer = new Timer();
        mouseClickChecker = new MouseClickChecker(robot);
        preventAFKTimerTask = new preventAFK(robot);
    }

    public void searchArea() {
        System.out.println("Starting Search");
        mouseTimer.schedule(mouseClickChecker, SEC_IN_MS * 30, SEC_IN_MS * 30);
        mouseTimer.schedule(preventAFKTimerTask, SEC_IN_MS * 60 * 4, SEC_IN_MS * 60 * 4);
        while(true) {
            radarSearch(Colors.FEATHERS);
        }
    }

    private void radarSearch(Colors color) {
        rect  = user32Controller.getWindowAsRECT(windowName);
        BufferedImage screenCap = robot.createScreenCapture(rect.toRectangle());
        IntegerPoint center  = new IntegerPoint(screenCap.getWidth()/2,screenCap.getHeight()/2);
        outerloop:
        for(int radius = 0;radius < screenCap.getWidth()/2;radius++) {
            HashSet<IntegerPoint> circumfrence = calculatePointsOnCircumfrence(radius,center);
            for(IntegerPoint point: circumfrence) {
                if(equalPixelColor(screenCap, point, color)) {
                    mouseAction(point);
                    sleep(calculateSleep(center, point));
                    break outerloop;
                }
            }
        }
    }


    private boolean equalPixelColor(BufferedImage image,IntegerPoint point, Colors color) {
        if(validatePoint(point,image)) {
            Color pixelColor = new Color(image.getRGB(point.getX(),point.getY()));
            return color.equals(pixelColor);
        }
        return false;
    }


    private HashSet<IntegerPoint> calculatePointsOnCircumfrence(int radius, IntegerPoint center) {
        HashSet<IntegerPoint> integerPoints = new HashSet<IntegerPoint>();
        double slice = (2*Math.PI)/360;
        for(int i = 0; i < 360; i++) {
            double angle = slice*i;
            int newX = (int)(center.getX() + radius * Math.cos(angle));
            int newY = (int)(center.getY() + radius * Math.sin(angle));
            boolean add = true;
            for(IntegerPoint point: integerPoints) {
                if(point.getX() == newX && point.getY() == newY) {
                    add = false;
                }
            }
            if(add) {
                integerPoints.add(new IntegerPoint(newX, newY));
            }
        }
        return integerPoints;
    }


    private void mouseAction(IntegerPoint point) {
        //Stop spinning
        mouseClickChecker.stop();
        //Prevent mouse afk click
        preventAFKTimerTask.toggleClickMouseFalse();
        IntegerPoint min_coor = new IntegerPoint((int) rect.toRectangle().getMinX(),(int) rect.toRectangle().getMinY());
        robot.mouseMove(min_coor.getX() + point.getX(), min_coor.getY() + point.getY());
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    private boolean validatePoint(IntegerPoint p, BufferedImage bufferedImage) {
        int inventory_x =  (bufferedImage.getWidth() - INVENTORY_FULLSCREEN_SIZE_X_Y  >= 0) ?
                bufferedImage.getWidth()-INVENTORY_FULLSCREEN_SIZE_X_Y :
                bufferedImage.getWidth()-INVENTORY_WINDOWED_SIZE_X_Y ;
        int inventory_y =  (bufferedImage.getWidth() - INVENTORY_FULLSCREEN_SIZE_X_Y >= 0) ?
                bufferedImage.getHeight()-INVENTORY_FULLSCREEN_SIZE_X_Y :
                bufferedImage.getHeight()-INVENTORY_WINDOWED_SIZE_X_Y;
        return p.getX() >= 0 && p.getY() >= 0 &&
                p.getX() < bufferedImage.getWidth() &&
                p.getY() < bufferedImage.getHeight() &&
                ((p.getX() <= inventory_x) || (p.getY() <= inventory_y));
    }

    private long calculateSleep(IntegerPoint center, IntegerPoint point) {
        int x = (center.getX() > point.getX()) ? (center.getX()- point.getX()) : point.getX() - center.getX();
        int y = (center.getY() > point.getY()) ? (center.getY() - point.getY()) : point.getY() - center.getY();
        return ((long) (Math.sqrt(Math.pow(x,2) +
                Math.pow(y,2))/PIXELS_PER_SQUARE)
                * TIME_TO_TRAVERSE_ONE_SQAURE_MS)
                + SERVER_LATENCY;
    }

    private void sleep(long millis) {
        try {
            System.out.println(millis);
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            return;
        }
    }

}
