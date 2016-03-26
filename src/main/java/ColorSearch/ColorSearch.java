package ColorSearch;

import IntegerPoint.IntegerPoint;
import ProcessLoader.ProcessLoader;
import User32.User32Controller;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Sam Roberts on 3/25/2016.
 */
public class ColorSearch {
    //Used to offset ad bar in Fullscreen
    private static final int AD_PIXEL_OFFSET_FULLSCREEN = 125;
    //Used to offset ad bar in Windowed
    private static final int AD_PIXEL_OFFSET_WINDOWED = 150;
    private User32Controller user32Controller;
    private Robot robot;
    private WinDef.RECT rect;

    public ColorSearch(String windowName) {
        user32Controller = new User32Controller();
        this.rect = user32Controller.getWindowAsRECT(windowName);
        try {
            robot = new Robot();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public void searchArea() {
        //Get top left corner
        IntegerPoint min_coor = new IntegerPoint(((int) rect.toRectangle().getMinX()),(int) rect.toRectangle().getMinY());
        IntegerPoint max_coor = new IntegerPoint((int) rect.toRectangle().getMaxX(), (int) rect.toRectangle().getMaxY());

        IntegerPoint point;
        System.out.println("Starting Search");
        while ((point = searchFromMinToMax(Colors.IRON,false)) == null);
        robot.mouseMove(min_coor.getX() + point.getX(),min_coor.getY() + point.getY());
        while(this.findInventory() == null);
    }

    /**
     * This function takes a screen cap of runescape and searches it for a particular ore color
     * @param oreColor Color of the ore being searched for.
     * @return Coordinates of where color is located, or null if no coordinates have been found
     */
    private IntegerPoint searchFromMinToMax(Colors oreColor , boolean findTransparent) {
        BufferedImage screenCap = robot.createScreenCapture(rect.toRectangle());
        for(int y = AD_PIXEL_OFFSET_WINDOWED; y < screenCap.getHeight(); y++) {
            for (int x = 0; x < screenCap.getWidth(); x++) {
                Color pixelColor = new Color(screenCap.getRGB(x,y));
                if(!findTransparent) {
                    if (oreColor.equals(pixelColor)) {
                        System.out.println(String.format("Found at x: %d,y: %d",x,y));
                        return new IntegerPoint(x, y);
                    }
                } else {
                    if(isTransparent(pixelColor)) {
                        System.out.println(String.format("Found at x: %d,y: %d",x,y));
                    }
                }
            }
        }
        return null;
    }

    private Rectangle findInventory() {
        IntegerPoint p1 = searchFromMinToMax(null,true);
        if(p1 != null) {
            System.out.println("FOUND!");
        }
        return null;
    }

    private boolean isTransparent(Color c) {
        return c.getTransparency() == Transparency.TRANSLUCENT;
    }
}
