package RunnableListener;

import com.sun.istack.internal.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by Sam Roberts on 3/23/2016.
 */
public class RunListener implements EventListener {
    private ExecutorService executorService;

    private List<Runnable> runnableList;

    public RunListener(ExecutorService executorService) {
        runnableList = new ArrayList<Runnable>();
        this.executorService = executorService;
    }

    public void add(@NotNull Runnable runnable) {
        runnableList.add(runnable);
    }

    public List<Runnable> getRunnableList() {
        return runnableList;
    }


    public void actionPerformed(Object source) {
        Runnable tempRunnable;
        if(source instanceof Runnable) {
            tempRunnable = (Runnable)source;
            runnableList.remove(tempRunnable);
        }
        if(runnableList.size() == 0) {
            executorService.shutdown();
        }
    }
}
