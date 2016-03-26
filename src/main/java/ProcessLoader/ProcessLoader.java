package ProcessLoader;

import ProcessLoader.Exceptions.ProcessLaunchException;

import java.io.IOException;
import java.util.EventObject;

/**
 * Created by Sam Roberts on 3/23/2016.
 */
public class ProcessLoader {

    private Process process;
    private static final String DEFAULT_PATH = "C:\\Users\\Sam Roberts\\jagexcache\\jagexlauncher\\bin\\JagexLauncher.exe";
    private static final String VERSION = "oldschool";

    public ProcessLoader() {
        process = null;
    }

    public Process LaunchProcess() throws ProcessLaunchException {
        return LaunchProcess(DEFAULT_PATH);
    }

    public Process LaunchProcess(String Path) throws ProcessLaunchException {
        String path = (Path != null) ? Path : DEFAULT_PATH;
        try {
            this.process = new ProcessBuilder(path, VERSION).start();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new ProcessLaunchException();
        }
        return process;
    }

    public void killProcess() {
        if(process != null) {
            process.destroy();
        }
    }
}
