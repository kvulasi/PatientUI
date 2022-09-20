package com.example.swthealthcare.ui;

import org.eclipse.swt.widgets.TableItem;

import com.example.swthealthcare.models.Patient;
/**
 * Class declared to add items in the table row.
 */
public class TableRowUI {
	/**
	 * Fill the table row with the given patient data.
	 * 
	 * @param item    {@link TableItem}
	 * @param patient {@link Patient}
	 */
	public void tableItems(TableItem item, Patient patient) {
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
