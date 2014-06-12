package fi.helsinki.cs.plugin.tmc.async.tasks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import fi.helsinki.cs.plugin.tmc.async.BackgroundTask;
import fi.helsinki.cs.plugin.tmc.async.StopStatus;
import fi.helsinki.cs.plugin.tmc.async.TaskFeedback;
import fi.helsinki.cs.plugin.tmc.domain.SubmissionResult;
import fi.helsinki.cs.plugin.tmc.services.ProjectUploader;
import fi.helsinki.cs.plugin.tmc.services.http.SubmissionResponse;

public class PastebinTaskTest {
    private ProjectUploader uploader;
    private TaskFeedback progress;
    private PastebinTask task;

    @Before
    public void setup() {
        uploader = mock(ProjectUploader.class);
        progress = mock(TaskFeedback.class);
        task = new PastebinTask(uploader, "path", "pastemessage");
    }

    @Test
    public void hasCorrectDescription() {
        assertEquals("Creating a pastebin", task.getDescription());
    }

    @Test
    public void taskReturnsSuccessAfterProperInitializationOfAllComponents() throws IOException {
        when(progress.isCancelRequested()).thenReturn(false);

        assertEquals(BackgroundTask.RETURN_SUCCESS, task.start(progress));

        verify(progress, times(2)).incrementProgress(1);
        verify(uploader, times(1)).setAsPaste("pastemessage");
        verify(uploader, times(1)).zipProjects();
        verify(uploader, times(1)).handleSumissionResponse();
    }

    @Test
    public void taskReturnsFailureIfCancelIsRequestedAtFirstCheckpoint() throws IOException {
        when(progress.isCancelRequested()).thenReturn(true);

        assertEquals(BackgroundTask.RETURN_FAILURE, task.start(progress));

        verify(progress, times(0)).incrementProgress(1);
    }

    @Test
    public void taskReturnsFailureIfCancelIsRequestedAtSecondCheckpoint() throws IOException {
        when(progress.isCancelRequested()).thenReturn(false);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                when(progress.isCancelRequested()).thenReturn(true);
                return null;
            }
        }).when(uploader).handleSumissionResponse();

        assertEquals(BackgroundTask.RETURN_FAILURE, task.start(progress));

        verify(progress, times(1)).incrementProgress(1);
    }

    @Test
    public void taskReturnsFailureIfAnExceptionIsThrown() throws IOException {
        when(progress.isCancelRequested()).thenReturn(false);
        doThrow(new IOException()).when(uploader).zipProjects();
        assertEquals(BackgroundTask.RETURN_FAILURE, task.start(progress));
    }

    @Test
    public void getPasteUrlReturnsTheUrlFromTheResponse() throws URISyntaxException {
        SubmissionResponse response = new SubmissionResponse(new URI("submission"), new URI("paste"));
        when(uploader.getResponse()).thenReturn(response);

        assertEquals("paste", task.getPasteUrl());
    }
}