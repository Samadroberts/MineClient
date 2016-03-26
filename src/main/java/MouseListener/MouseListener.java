package MouseListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam Roberts on 3/25/2016.
 */
public class MouseListener {

    private List<Point2D> point2DList;
    private static final int DEFAULT_RECORD_INTERVAL = 100;
    private boolean record = false;
    private Thread mouseRecordThread;

    public MouseListener() {
        point2DList = new ArrayList<Point2D>();
    }

    public void startRecording() {
        Runnable runnable = new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(DEFAULT_RECORD_INTERVAL);
                        Point point = MouseInfo.getPointerInfo().getLocation();
                        Log(point.getX(),point.getY());
                    } catch (InterruptedException e) {
                        //Interrupted exit
                    }
                }
            }
        };
        mouseRecordThread = new Thread(runnable);
        mouseRecordThread.start();
    }

    public void stopRecording() {
        if(mouseRecordThread != null) {
            mouseRecordThread.interrupt();
        }
    }

    public List<Point2D> getPoint2DList() {
        return point2DList;
    }

    public boolean isRecording() {
        return !mouseRecordThread.isInterrupted();
    }

    private void Log(double x, double y) {
        point2DList.add(new Point2D.Double(x,y));
    }

    public void printCoordinates() {
        for(Point2D point2D : point2DList) {
            System.out.println(String.format("x: %d || y: %d", point2D.getX(), point2D.getY()));
        }
    }
}
