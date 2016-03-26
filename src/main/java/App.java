import ProcessLoader.Exceptions.ProcessLaunchException;
import ProcessLoader.ProcessLoader;
import RunEvents.RunnableEvent;
import RunnableListener.RunListener;

import java.io.*;

/**
 * Created by Sam Roberts on 3/23/2016.
 */
public class App implements Runnable {

    private ProcessLoader processLoader;
    private RunnableEvent runnableEvent;

    public App (RunListener runListener) {
        processLoader = new ProcessLoader();
        runnableEvent = new RunnableEvent(this);
        runnableEvent.addListener(runListener);
    }

    public void run() {
        try {
            startProcess();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
    private void startProcess() throws ProcessLaunchException {
        Process process = processLoader.LaunchProcess();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.print("Process Stopped Unexpectedly");
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            processLoader.killProcess();
            runnableEvent.sendAction();
        }
    }
}
