package ColorSearch;

import java.awt.*;

/**
 * Created by Sam Roberts on 3/25/2016.
 */
public enum Colors {
    //IRON(13,70,3,37,3,25),
    IRON(55,59,28,31,19,21),
    INVENTORY_BORDER(0,0,0,0,1,1);


    private int red_min;
    private int red_max;
    private int red_tolerence;
    private int green_min;
    private int green_max;
    private int green_tolerence;
    private int blue_min;
    private int blue_max;
    private int blue_tolerence;

    Colors(int red_min, int red_max, int green_min, int green_max, int blue_min, int blue_max) {
        this.red_min = red_min;
        this.red_max = red_max;
        this.red_tolerence =  calculateTolerence(red_max,red_min);
        this.green_min = green_min;
        this.green_max = green_max;
        this.green_tolerence = calculateTolerence(green_max,green_min);
        this.blue_min = blue_min;
        this.blue_max = blue_max;
        this.blue_tolerence = calculateTolerence(blue_max,blue_min);
    }

    public boolean equals(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return (red >= red_min) && (red <= red_max) &&
                (green >= green_min) && (green <= green_max) &&
                (blue >= blue_min) && (blue <= blue_max);
    }

    private int calculateTolerence(int max, int min) {
        return Math.abs(max-min);
    }

    private int calculateAvgRed(Colors color) {
        return (color.getRed_max() + color.getRed_min())/2;
    }

    private int calculateAvgGreen(Colors color) {
        return (color.getGreen_max() + color.getGreen_min())/2;
    }

    private int calculateAvgBlue(Colors color) {
        return (color.getBlue_max() + color.getBlue_min())/2;
    }

    public int getRed_min() {
        return red_min;
    }

    public int getRed_max() {
        return red_max;
    }

    public int getRed_tolerence() {
        return red_tolerence;
    }

    public int getGreen_min() {
        return green_min;
    }

    public int getGreen_max() {
        return green_max;
    }

    public int getGreen_tolerence() {
        return green_tolerence;
    }

    public int getBlue_min() {
        return blue_min;
    }

    public int getBlue_max() {
        return blue_max;
    }

    public int getBlue_tolerence() {
        return blue_tolerence;
    }
}
