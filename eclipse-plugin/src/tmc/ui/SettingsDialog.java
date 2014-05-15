package tmc.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import fi.helsinki.cs.plugin.tmc.services.CourseFetcher;
import fi.helsinki.cs.plugin.tmc.services.Settings;

public class SettingsDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text userNameText;
	private Text passWordText;
	private Text serverAddress;
	private Text text;
	private Settings settings;
	private CourseFetcher courseFetcher;
	private Label lblErrorText;

	public SettingsDialog(Shell parent, int style, Settings settings, CourseFetcher courseFetcher) {
		super(parent, style);
		setText("Settings");
		this.settings = settings;
		this.courseFetcher = courseFetcher;
	}

	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/* Generated by WindowBuilder-tool */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(548, 483);
		shell.setText(getText());
		
		lblErrorText = new Label(shell, SWT.NONE);
		lblErrorText.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		lblErrorText.setBounds(10, 10, 430, 17);
		lblErrorText.setText("");
		
		Label lblUserName = new Label(shell, SWT.NONE);
		lblUserName.setBounds(10, 44, 77, 17);
		lblUserName.setText("Username");
		
		userNameText = new Text(shell, SWT.BORDER);
		userNameText.setBounds(154, 44, 259, 27);
		userNameText.setText(settings.getUsername());
		
		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setBounds(10, 83, 70, 17);
		lblPassword.setText("Password");
		
		passWordText = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		passWordText.setBounds(154, 77, 259, 27);
		passWordText.setText(settings.getPassword());
		
		Label lblServerAddress = new Label(shell, SWT.NONE);
		lblServerAddress.setText("Server Address");
		lblServerAddress.setBounds(10, 117, 123, 17);
		
		serverAddress = new Text(shell, SWT.BORDER);
		serverAddress.setBounds(154, 110, 386, 27);
		serverAddress.setText(settings.getServerBaseUrl());
		
		Button btnSavePassword = new Button(shell, SWT.CHECK);
		btnSavePassword.setBounds(419, 77, 131, 24);
		btnSavePassword.setText("Save Password");
		
		Label lblCurrentCourse = new Label(shell, SWT.NONE);
		lblCurrentCourse.setText("Current course");
		lblCurrentCourse.setBounds(10, 151, 123, 17);
		
		Combo combo = new Combo(shell, SWT.READ_ONLY);
		combo.setBounds(154, 143, 259, 29);
		combo.setItems(courseFetcher.getCourseNames());
		
		Button btnRefreshCourses = new Button(shell, SWT.NONE);
		btnRefreshCourses.setBounds(419, 143, 121, 29);
		btnRefreshCourses.setText("Refresh");
		btnRefreshCourses.addSelectionListener(new SelectionAdapter() {
		      @Override
		      public void widgetSelected(SelectionEvent e) {
		    	courseFetcher.updateCourses();
		        lblErrorText.setText("Senkin idiootti");
		      }
		    });
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 188, 530, 2);
		
		Label lblFolder = new Label(shell, SWT.NONE);
		lblFolder.setText("Folder for projects");
		lblFolder.setBounds(10, 206, 138, 17);
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setBounds(449, 410, 91, 29);
		btnCancel.setText("Cancel");
		btnCancel.addSelectionListener(new SelectionAdapter() {
		      @Override
		      public void widgetSelected(SelectionEvent e) {
		        shell.close();
		      }
		    });
		
		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.setText("OK");

		btnOk.setBounds(349, 410, 91, 29);
		btnOk.addSelectionListener(new SelectionAdapter() {
		      @Override
		      public void widgetSelected(SelectionEvent e) {
		    	settings.setUsername(userNameText.getText());
		    	settings.setPassword(passWordText.getText());
		    	settings.setServerBaseUrl(serverAddress.getText());
		    	settings.save();
		        shell.close();
		      }
		    });
		
		text = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text.setBounds(154, 206, 259, 27);
		
		Button btnBrowse = new Button(shell, SWT.NONE);
		btnBrowse.setText("Browse...");
		btnBrowse.setBounds(419, 204, 121, 29);
		
		Label label_1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(10, 239, 530, 2);
		
		Button btnCheckFor = new Button(shell, SWT.CHECK);
		btnCheckFor.setSelection(true);
		btnCheckFor.setText("Check for new or updated exercises regularly");
		btnCheckFor.setBounds(10, 263, 403, 24);
		
		Button btnCheckThat = new Button(shell, SWT.CHECK);
		btnCheckThat.setSelection(true);
		btnCheckThat.setText("Check that all active active exercises are open on startup");
		btnCheckThat.setBounds(10, 293, 430, 24);
		
		Button btnSendSnapshotsOf = new Button(shell, SWT.CHECK);
		btnSendSnapshotsOf.setText("Send snapshots of your progress for study");
		btnSendSnapshotsOf.setSelection(true);
		btnSendSnapshotsOf.setBounds(10, 323, 430, 24);
		
		Label label_2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(10, 353, 530, 2);
		
		Label lblPreferredErrorMessage = new Label(shell, SWT.NONE);
		lblPreferredErrorMessage.setText("Preferred error message language");
		lblPreferredErrorMessage.setBounds(10, 376, 236, 17);
		
		Combo combo_1 = new Combo(shell, SWT.READ_ONLY);
		combo_1.setItems(new String[] {"English", "Finnish"});
		combo_1.setBounds(252, 375, 284, 29);
		combo_1.select(1);

	}
}
