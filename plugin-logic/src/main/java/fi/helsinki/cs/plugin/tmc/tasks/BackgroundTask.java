package fi.helsinki.cs.plugin.tmc.tasks;

public interface BackgroundTask {

    public Object start(TaskFeedback feedback);
    public void stop();
    public String getName();
    
}