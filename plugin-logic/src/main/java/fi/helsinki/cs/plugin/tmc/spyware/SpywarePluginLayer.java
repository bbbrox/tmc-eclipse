package fi.helsinki.cs.plugin.tmc.spyware;

import java.io.Closeable;

import fi.helsinki.cs.plugin.tmc.io.FileIO;
import fi.helsinki.cs.plugin.tmc.spyware.services.EventDeduplicater;
import fi.helsinki.cs.plugin.tmc.spyware.services.EventReceiver;
import fi.helsinki.cs.plugin.tmc.spyware.services.EventSendBuffer;
import fi.helsinki.cs.plugin.tmc.spyware.services.EventStore;
import fi.helsinki.cs.plugin.tmc.spyware.services.SnapshotTaker;
import fi.helsinki.cs.plugin.tmc.spyware.utility.ActiveThreadSet;

public class SpywarePluginLayer implements Closeable {
    private ActiveThreadSet activeThreads;
    private EventReceiver receiver;

    public SpywarePluginLayer(ActiveThreadSet activeThreads) {
        this.activeThreads = activeThreads;
        receiver = new EventDeduplicater(new EventSendBuffer(new EventStore(new FileIO("sikrit.tmp"))));
    }

    public void takeSnapshot(SnapshotInfo info) {
        (new SnapshotTaker(info, activeThreads, receiver)).execute();
    }

    @Override
    public void close() {
        // TODO run in a separate thread
        try {
            activeThreads.joinAll();
        } catch (InterruptedException e) {
            // do nothing
        }
    }

}