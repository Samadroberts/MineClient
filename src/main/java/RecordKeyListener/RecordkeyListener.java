package RecordKeyListener;

import MouseListener.MouseListener;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Sam Roberts on 3/25/2016.
 */
public class RecordkeyListener implements KeyListener {

    private MouseListener mouseListener;
    private static final int alt = KeyEvent.VK_ALT;
    private static final int rightPos = KeyEvent.KEY_LOCATION_RIGHT;

    public RecordkeyListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    //Ignore
    public void keyTyped(KeyEvent e) {
        return;
    }

    public void keyPressed(KeyEvent e) {
        if((e.getKeyCode() == alt) && (e.getKeyLocation() == rightPos)) {
            if(!mouseListener.isRecording()) {
                mouseListener.startRecording();
            } else {
                mouseListener.stopRecording();
            }
        }
    }

    //Ignore
    public void keyReleased(KeyEvent e) {
        return;
    }
}
