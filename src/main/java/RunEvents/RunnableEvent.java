package RunEvents;

import RunnableListener.RunListener;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 * Created by Sam Roberts on 3/23/2016.
 */
public class RunnableEvent extends EventObject {

    public List<RunListener> runListeners;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public RunnableEvent(Object source) {
        super(source);
        runListeners = new ArrayList<RunListener>();
    }

    public void addListener(RunListener runListener) {
        runListeners.add(runListener);
    }

    public void removeListener(RunListener runListener) {
        runListeners.remove(runListener);
    }

    public void sendAction() {
        for(RunListener runListener: runListeners) {
            runListener.actionPerformed(getSource());
        }
    }


}
