package com.example.swthealthcare;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.swthealthcare.constants.AppConstants;
import com.example.swthealthcare.httpclient.PatientHttpClient;
import com.example.swthealthcare.models.Patient;
import com.example.swthealthcare.patientui.PatientUI;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.util.List;
/**
 * Class declared for spring boot main method.
 */
@SpringBootApplication
public class SWTHealthCareApplication {
	static Table table = null;
	/**
	 * Main method.
	 * 
	 * @param args {@link Array} of {@link String}
	 * @throws {@link IOException}
	 * @throws {@link InterruptedException}
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		PatientUI patientUI = new PatientUI();
		PatientHttpClient patientHttpClient = new PatientHttpClient();
		List<Patient> patients = patientHttpClient.getAllPatients();

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText(AppConstants.PATIENTS_UI);
		shell.setLayout(new GridLayout());

		Listener listener = event -> {
			Text searchText = (Text) event.widget;
			try {
				List<Patient> searchPatientsList = patientHttpClient.getPatientsBySearch(searchText.getText());
				fillTable(searchPatientsList);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};

		Text searchText;
		searchText = new Text(shell, SWT.SEARCH);
		searchText.setMessage("search");
		searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchText.addListener(SWT.DefaultSelection, listener);

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
		fillTable(patients);

		Button create = new Button(shell, SWT.PUSH);
		create.setText(AppConstants.CREATE_BUTTON);
		
		Button update = new Button(shell, SWT.LEFT);
		update.setText(AppConstants.UPDATE_BUTTON);
		update.setEnabled(false);
		
		Button delete = new Button(shell, SWT.LEFT);
		delete.setText(AppConstants.DELETE_BUTTON);
		delete.setEnabled(false);
		
		Button view = new Button(shell, SWT.LEFT);
		view.setText(AppConstants.VIEW_BUTTON);
		view.setEnabled(false);
		
		table.addListener(SWT.Selection, event -> {
			update.setEnabled(true);
			view.setEnabled(true);
			delete.setEnabled(true);
		});
		
		view.addListener(SWT.Selection, event -> {
			String patientId = null;
			for (TableItem item : table.getSelection()) {
				patientId = "" + item.getText(0);
			}

			try {
				patientUI.patientViewFill(table, display, AppConstants.PATIENT_VIEW, view.getText(),
						Long.valueOf(patientId));
				view.setEnabled(false);
				delete.setEnabled(false);
				update.setEnabled(false);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		update.addListener(SWT.Selection, event -> {
			update.setEnabled(true);
			String patientId = null;
			for (TableItem item : table.getSelection()) {
				patientId = "" + item.getText(0);
			}
			if (patientId != null) {
				try {
					patientUI.patientViewFill(table, display, AppConstants.PATIENT_UPDATE, update.getText(),
							Long.valueOf(patientId));
					view.setEnabled(false);
					delete.setEnabled(false);
					update.setEnabled(false);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		create.addListener(SWT.Selection, event -> {
			try {
				patientUI.patientViewFill(table, display, AppConstants.PATIENT_CREATE, create.getText(), null);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		delete.addListener(SWT.Selection, event -> {
			String patientId = null;
			for (TableItem item : table.getSelection()) {
				patientId = "" + item.getText(0);
			}
			try {
				int statusCode = patientHttpClient.deletePatientById(Long.valueOf(patientId));
				if (statusCode == 200) {
					table.remove(table.getSelectionIndices());
				}
				view.setEnabled(false);
				delete.setEnabled(false);
				update.setEnabled(false);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	/**
	 * Fill the table with the patients list.
	 * 
	 * @param patients {@link List} of {@link Patient}
	 */
	private static void fillTable(List<Patient> patients) {
		table.removeAll();
		if (!patients.isEmpty()) {
			final TableItem[] items = new TableItem[patients.size()];

			for (int i = 0; i < patients.size(); i++) {
				new TableColumn(table, SWT.NONE).setWidth(40);
			}
			for (int i = 0; i < patients.size(); i++) {
				items[i] = new TableItem(table, SWT.NONE);
				tableItems(items[i], patients.get(i));
			}
		}
	}
	/**
	 * Fill the table row with the patients.
	 * 
	 * @param item {@link TableItem}
	 * @param patient {@link Patient}
	 */
	public static void tableItems(TableItem item, Patient patient) {
		item.setText(0, String.valueOf(patient.getId()));
		item.setText(1, patient.getFirstName());
		item.setText(2, patient.getLastName());
		item.setText(3, patient.getGender());
		item.setText(4, patient.getDateOfBirth().toString());
		item.setText(5, patient.getAddresses().toString());
		item.setText(6, patient.getPhoneNumber());
		item.setText(7, patient.getSecondaryPhoneNumber() == null ? "" : patient.getSecondaryPhoneNumber().toString());
		item.setText(8, patient.getLandLineNumber() == null ? "" : patient.getLandLineNumber().toString());
	}
}
