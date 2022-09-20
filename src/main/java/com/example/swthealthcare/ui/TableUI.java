package com.example.swthealthcare.ui;

import java.io.IOException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.example.swthealthcare.constants.AppConstants;
import com.example.swthealthcare.httpclient.PatientHttpClient;
import com.example.swthealthcare.models.Patient;
/**
 * Class declared to create table in SWT UI.
 */
public class TableUI {
	Table table = null;
	Button create = null, update = null, delete = null, view = null;

	PatientHttpClient patientHttpClient = new PatientHttpClient();
	PatientUI patientUI = new PatientUI();
	TableRowUI tableRowUI = new TableRowUI();

	/**
	 * Method defined to create table view.
	 */
	public void tableUI() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText(AppConstants.PATIENTS_UI);
		shell.setLayout(new GridLayout());

		Text searchText;
		searchText = new Text(shell, SWT.SEARCH | SWT.ICON_CANCEL);
		searchText.setMessage(AppConstants.SEARCH);
		searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		String[] titles = { AppConstants.PATIENT_ID, AppConstants.FIRST_NAME, AppConstants.LAST_NAME,
				AppConstants.GENDER, AppConstants.DATE_OF_BIRTH, AppConstants.ADDRESSES, AppConstants.PHONE_NUMBER,
				AppConstants.SEC_PHONE_NUMBER, AppConstants.LANDLINE_NUMBER };
		for (String title : titles) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(title);
		}

		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		try {
			fillTable(patientHttpClient.getAllPatients());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		create = new Button(shell, SWT.PUSH);
		create.setText(AppConstants.CREATE_BUTTON);

		update = new Button(shell, SWT.LEFT);
		update.setText(AppConstants.UPDATE_BUTTON);
		update.setEnabled(false);

		delete = new Button(shell, SWT.LEFT);
		delete.setText(AppConstants.DELETE_BUTTON);
		delete.setEnabled(false);

		view = new Button(shell, SWT.LEFT);
		view.setText(AppConstants.VIEW_BUTTON);
		view.setEnabled(false);

		table.addListener(SWT.Selection, event -> {
			update.setEnabled(true);
			view.setEnabled(true);
			delete.setEnabled(true);
		});

		searchText.addListener(SWT.DefaultSelection, getSearchListener());
		view.addListener(SWT.Selection, getButtonsListener(display));
		update.addListener(SWT.Selection, getButtonsListener(display));
		delete.addListener(SWT.Selection, getButtonsListener(display));
		create.addListener(SWT.Selection, getButtonsListener(display));

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	/**
	 * Method defined to create listener for create, update, view and delete buttons.
	 * @param display {@link Display}
	 */
	private Listener getButtonsListener(Display display) {
		Listener listener = event -> {
			Button button = (Button) event.widget;
			String patientId = null;
			for (TableItem item : table.getSelection()) {
				patientId = "" + item.getText(0);
			}
			if(AppConstants.VIEW_BUTTON.equalsIgnoreCase(button.getText())) {
				try {
					patientUI.patientViewFill(table, display, AppConstants.PATIENT_VIEW, view.getText(), Long.valueOf(patientId));
					disableButtons();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(AppConstants.DELETE_BUTTON.equalsIgnoreCase(button.getText())) {
				try {
					int statusCode = patientHttpClient.deletePatientById(Long.valueOf(patientId));
					if (statusCode == 200) {
						table.remove(table.getSelectionIndices());
					}
					disableButtons();
				} catch (NumberFormatException | IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(AppConstants.UPDATE_BUTTON.equalsIgnoreCase(button.getText())) {
				try {
					patientUI.patientViewFill(table, display, AppConstants.PATIENT_UPDATE, update.getText(),
							Long.valueOf(patientId));
				} catch (NumberFormatException | IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				disableButtons();
			} else if(AppConstants.CREATE_BUTTON.equalsIgnoreCase(button.getText())) {
				try {
					patientUI.patientViewFill(table, display, AppConstants.PATIENT_CREATE, create.getText(), null);
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				disableButtons();
			}
		};
		return listener;
	}
	/**
	 * Method defined to create listener for search.
	 * @return {@link Listener}
	 */
	private Listener getSearchListener() {
		Listener listener = event -> {
			create.setEnabled(false);
			Text searchKeyword = (Text) event.widget;
			List<Patient> searchPatientsList = null;
			try {
				searchPatientsList = patientHttpClient.getPatientsBySearch(searchKeyword.getText());
				fillTable(searchPatientsList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		return listener;
	}
	/**
	 * Method defined to make update, delete and view buttons disable.
	 */
	private void disableButtons() {
		view.setEnabled(false);
		delete.setEnabled(false);
		update.setEnabled(false);
	}
	
	/**
	 * Fill the table with the patients list.
	 * 
	 * @param patients {@link List} of {@link Patient}
	 */
	private void fillTable(List<Patient> patients) {
		table.removeAll();
		if (!patients.isEmpty()) {
			final TableItem[] items = new TableItem[patients.size()];

			for (int i = 0; i < patients.size(); i++) {
				new TableColumn(table, SWT.NONE).setWidth(40);
			}
			for (int i = 0; i < patients.size(); i++) {
				items[i] = new TableItem(table, SWT.NONE);
				tableRowUI.tableItems(items[i], patients.get(i));
			}
		}
	}
}
