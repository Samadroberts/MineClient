import ColorSearch.ColorSearch;
import MouseListener.MouseListener;
import RecordKeyListener.RecordkeyListener;
import RunnableListener.RunListener;
import User32.User32Controller;
import com.sun.jna.platform.win32.WinDef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sam Roberts on 3/23/2016.
 */
public class Main {
    private static App RuneScape;
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static MouseListener mouseListener = new MouseListener();
    private static RecordkeyListener keyListener;
    private static final String RUNESCAPE_WINDOW_NAME = "Old School RuneScape";

    public static void main(String args[]){
        //Start RunScape
        RuneScape = new App(new RunListener(executorService));

        executorService.submit(RuneScape);
        //Do Other shit

        keyListener = new RecordkeyListener(mouseListener);

        User32Controller user32Controller = new User32Controller();
        while(!user32Controller.setTopMost(RUNESCAPE_WINDOW_NAME));
        int i = 0;
        while (i < 1000) {i++;}

        ColorSearch colorSearch = new ColorSearch(RUNESCAPE_WINDOW_NAME);
        colorSearch.searchArea();
        //Wait on Runescape to close before shutting exiting
        while(!executorService.isShutdown());
        mouseListener.stopRecording();
        System.out.println("Shutting Down");
    }
}
