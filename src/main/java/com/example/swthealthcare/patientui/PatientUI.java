package com.example.swthealthcare.patientui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.example.swthealthcare.SWTHealthCareApplication;
import com.example.swthealthcare.constants.AppConstants;
import com.example.swthealthcare.httpclient.PatientHttpClient;
import com.example.swthealthcare.models.Address;
import com.example.swthealthcare.models.Patient;

public class PatientUI {
	PatientHttpClient patientClient = new PatientHttpClient();

	Text patientIdText, firstNameText, lastNameText, dobText, phoneNumberText, secondaryPhoneNumberText,
			landLineNumberText;
	Text permanentAddressIdText, permanentAddressText, permanentAddressCityText, permanentAddressStateText,
			permanentAddressCountryText, permanentAddressPincodeText;
	Text currentAddressIdText, currentAddressText, currentAddressCityText, currentAddressStateText,
			currentAddressCountryText, currentAddressPincodeText;
	Combo genderCombo;
	Group permanentAddressGroup, currentAddressGroup;

	int statusCode;
	Patient patientResponse;
	/**
	 * Method defined to show the patient data in UI.
	 * @param table {@link Table}
	 * @param display {@link Display}
	 * @param buttonType {@link String}
	 * @param viewName {@link String}
	 */
	private void patientView(Table table, Display display, String buttonType, String viewName) {
		Display patientDisplay = display;
		Shell patientShell = new Shell(patientDisplay, SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX | SWT.ON_TOP);
		patientShell.setText(viewName);
		patientShell.setLayout(new GridLayout(2, true));

		if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
			Label patientIdLabel = new Label(patientShell, SWT.NONE);
			patientIdLabel.setText("PatientId");
			patientIdText = new Text(patientShell, SWT.BORDER);
		}

		Label firstNameLabel = new Label(patientShell, SWT.NONE);
		firstNameLabel.setText(AppConstants.FIRST_NAME);
		firstNameText = new Text(patientShell, SWT.BORDER);

		Label lastNameLabel = new Label(patientShell, SWT.NONE);
		lastNameLabel.setText(AppConstants.LAST_NAME);
		lastNameText = new Text(patientShell, SWT.BORDER);

		Label genderLabel = new Label(patientShell, SWT.NONE);
		genderLabel.setText(AppConstants.GENDER);
		genderCombo = new Combo(patientShell, SWT.FILL);
		genderCombo.setItems(AppConstants.MALE, AppConstants.FEMALE);

		Label dobLabel = new Label(patientShell, SWT.NONE);
		dobLabel.setText(AppConstants.DATE_OF_BIRTH);
		dobText = new Text(patientShell, SWT.BORDER);
		dobText.setText("yyyy-MM-dd");

		permanentAddressGroup = new Group(patientShell, SWT.NONE);
		permanentAddressGroup.setText(AppConstants.PERMANENT_ADDRESS);
		permanentAddressGroup.setLayout(new GridLayout(2, true));
		permanentAddressGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
			Label permanentAddressIdLabel = new Label(permanentAddressGroup, SWT.NONE);
			permanentAddressIdLabel.setText(AppConstants.ADDRESS_ID);
			permanentAddressIdText = new Text(permanentAddressGroup, SWT.BORDER);
		}

		Label permanentAddressLabel = new Label(permanentAddressGroup, SWT.NONE);
		permanentAddressLabel.setText(AppConstants.ADDRESS);
		permanentAddressText = new Text(permanentAddressGroup, SWT.BORDER);

		Label permanentAddressCityLabel = new Label(permanentAddressGroup, SWT.NONE);
		permanentAddressCityLabel.setText(AppConstants.CITY);
		permanentAddressCityText = new Text(permanentAddressGroup, SWT.BORDER);

		Label permanentAddressStateLabel = new Label(permanentAddressGroup, SWT.NONE);
		permanentAddressStateLabel.setText(AppConstants.STATE);
		permanentAddressStateText = new Text(permanentAddressGroup, SWT.BORDER);

		Label permanentAddressCountryLabel = new Label(permanentAddressGroup, SWT.NONE);
		permanentAddressCountryLabel.setText(AppConstants.COUNTRY);
		permanentAddressCountryText = new Text(permanentAddressGroup, SWT.BORDER);

		Label permanentAddressPincodeLabel = new Label(permanentAddressGroup, SWT.NONE);
		permanentAddressPincodeLabel.setText(AppConstants.PINCODE);
		permanentAddressPincodeText = new Text(permanentAddressGroup, SWT.BORDER);

		currentAddressGroup = new Group(patientShell, SWT.NONE);
		currentAddressGroup.setText(AppConstants.CURRENT_ADDRESS);

		currentAddressGroup.setLayout(new GridLayout(2, true));
		currentAddressGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
			Label currentAddressIdLabel = new Label(currentAddressGroup, SWT.NONE);
			currentAddressIdLabel.setText(AppConstants.ADDRESS_ID);
			currentAddressIdText = new Text(currentAddressGroup, SWT.BORDER);
		}

		Label currentAddressLabel = new Label(currentAddressGroup, SWT.NONE);
		currentAddressLabel.setText(AppConstants.ADDRESS);
		currentAddressText = new Text(currentAddressGroup, SWT.BORDER);

		Label currentAddressCityLabel = new Label(currentAddressGroup, SWT.NONE);
		currentAddressCityLabel.setText(AppConstants.CITY);
		currentAddressCityText = new Text(currentAddressGroup, SWT.BORDER);

		Label currentAddressStateLabel = new Label(currentAddressGroup, SWT.NONE);
		currentAddressStateLabel.setText(AppConstants.STATE);
		currentAddressStateText = new Text(currentAddressGroup, SWT.BORDER);

		Label currentAddressCountryLabel = new Label(currentAddressGroup, SWT.NONE);
		currentAddressCountryLabel.setText(AppConstants.COUNTRY);
		currentAddressCountryText = new Text(currentAddressGroup, SWT.BORDER);

		Label currentAddressPincodeLabel = new Label(currentAddressGroup, SWT.NONE);
		currentAddressPincodeLabel.setText(AppConstants.PINCODE);
		currentAddressPincodeText = new Text(currentAddressGroup, SWT.BORDER);

		Label phoneNumberLabel = new Label(patientShell, SWT.NONE);
		phoneNumberLabel.setText(AppConstants.PHONE_NUMBER);
		phoneNumberText = new Text(patientShell, SWT.BORDER);

		Label secondaryPhoneNumberLabel = new Label(patientShell, SWT.NONE);
		secondaryPhoneNumberLabel.setText(AppConstants.SEC_PHONE_NUMBER);
		secondaryPhoneNumberText = new Text(patientShell, SWT.BORDER);

		Label landLineNumberLabel = new Label(patientShell, SWT.NONE);
		landLineNumberLabel.setText(AppConstants.LANDLINE_NUMBER);
		landLineNumberText = new Text(patientShell, SWT.BORDER);

		if (AppConstants.VIEW_BUTTON.equalsIgnoreCase(buttonType)) {
			firstNameText.setEditable(false);
			lastNameText.setEditable(false);
			genderCombo.setEnabled(false);
			dobText.setEditable(false);
			permanentAddressText.setEditable(false);
			permanentAddressText.setEditable(false);
			permanentAddressCityText.setEditable(false);
			permanentAddressStateText.setEditable(false);
			permanentAddressCountryText.setEditable(false);
			permanentAddressPincodeText.setEditable(false);
			currentAddressText.setEditable(false);
			currentAddressCityText.setEditable(false);
			currentAddressStateText.setEditable(false);
			currentAddressCountryText.setEditable(false);
			currentAddressPincodeText.setEditable(false);
			phoneNumberText.setEditable(false);
			secondaryPhoneNumberText.setEditable(false);
			landLineNumberText.setEditable(false);
		} else if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)
				|| AppConstants.CREATE.equalsIgnoreCase(buttonType)) {
			Button ok = new Button(patientShell, SWT.PUSH);
			ok.setText("submit");
			if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
				patientIdText.setEditable(false);
				permanentAddressIdText.setEditable(false);
				currentAddressIdText.setEditable(false);
			}
			ok.addSelectionListener(widgetSelectedAdapter(e -> {
				try {
					if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
						statusCode = patientClient.updatePatient(getPatient(AppConstants.UPDATE_BUTTON).getId(),
								getPatient(buttonType));
						if (statusCode == 200) {
							List<Patient> pl = patientClient.getAllPatients();
							for (int i = 0; i < pl.size(); i++) {
								SWTHealthCareApplication.tableItems(table.getItems()[i], pl.get(i));
							}
							patientShell.close();
						}
					} else {
						patientResponse = patientClient.addPatient(getPatient(AppConstants.CREATE_BUTTON));
						if (patientResponse != null) {
							TableItem item = new TableItem(table, SWT.NONE, patientClient.getAllPatients().size() - 1);
							SWTHealthCareApplication.tableItems(item, patientResponse);
							patientShell.close();
						}
					}
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}));
		}
		patientShell.pack();
		patientShell.open();
	}
	/**
	 * Method defined to get patient data from UI.
	 * @param buttonType {@link String}
	 * @return {@link Patient}
	 */
	private Patient getPatient(String buttonType) {
		Patient patient = new Patient();
		if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
			patient.setId(Long.valueOf(patientIdText.getText()));
		}
		patient.setFirstName(firstNameText.getText());
		patient.setLastName(lastNameText.getText());
		patient.setGender(genderCombo.getText());
		patient.setDateOfBirth(dobText.getText());

		List<Address> addresses = new ArrayList<>();
		Address permanentAddress = new Address();

		if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
			permanentAddress.setId(Long.valueOf(permanentAddressIdText.getText()));
			permanentAddress.setPatientId(Long.valueOf(patientIdText.getText()));
		}
		permanentAddress.setAddressType(permanentAddressGroup.getText());
		permanentAddress.setAddress(permanentAddressText.getText());
		permanentAddress.setCity(permanentAddressCityText.getText());
		permanentAddress.setState(permanentAddressStateText.getText());
		permanentAddress.setCountry(permanentAddressCountryText.getText());
		permanentAddress.setPincode(permanentAddressPincodeText.getText());
		addresses.add(permanentAddress);

		Address currentAddress = new Address();

		if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
			currentAddress.setId(Long.valueOf(currentAddressIdText.getText()));
			currentAddress.setPatientId(Long.valueOf(patientIdText.getText()));
		}
		currentAddress.setAddressType(currentAddressGroup.getText());
		currentAddress.setAddress(currentAddressText.getText());
		currentAddress.setCity(currentAddressCityText.getText());
		currentAddress.setState(currentAddressStateText.getText());
		currentAddress.setCountry(currentAddressCountryText.getText());
		currentAddress.setPincode(currentAddressPincodeText.getText());
		addresses.add(currentAddress);

		patient.setAddresses(addresses);

		patient.setPhoneNumber(phoneNumberText.getText());
		patient.setSecondaryPhoneNumber(secondaryPhoneNumberText.getText() != null ? secondaryPhoneNumberText.getText() : null);
		patient.setLandLineNumber(landLineNumberText.getText() != null ? landLineNumberText.getText() : null);
		return patient;
	}
	/**
	 * Method defined to fill the UI with patient data.
	 * @param table {@link Table}
	 * @param display {@link Display}
	 * @param viewName {@link String}
	 * @param buttonType {@link String}
	 * @param PatientId {@link Long}
	 * @throws {@link IOException}
	 * @throws {@link InterruptedException}
	 */
	public void patientViewFill(Table table, Display display, String viewName, String buttonType, Long PatientId)
			throws IOException, InterruptedException {
		patientView(table, display, buttonType, viewName);

		if (!AppConstants.CREATE_BUTTON.equalsIgnoreCase(buttonType)) {
			Patient patientById = patientClient.getPatientById(PatientId);
			if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
				patientIdText.setText(String.valueOf(patientById.getId()));
			}
			firstNameText.setText(patientById.getFirstName());
			lastNameText.setText(patientById.getLastName());
			genderCombo.setText(patientById.getGender());
			dobText.setText(patientById.getDateOfBirth());
			List<Address> addresses = patientById.getAddresses();

			for (int i = 0; i < addresses.size(); i++) {
				Address address = addresses.get(i);
				if (AppConstants.PERMANENT_ADDRESS.equals(address.getAddressType())) {
					if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
						permanentAddressIdText.setText(String.valueOf(address.getId()));
					}
					
					permanentAddressText.setText(address.getAddress());
					permanentAddressCityText.setText(address.getCity());
					permanentAddressStateText.setText(address.getState());
					permanentAddressCountryText.setText(address.getCountry());
					permanentAddressPincodeText.setText(address.getPincode());
				} else if (AppConstants.CURRENT_ADDRESS.equals(address.getAddressType())) {
					if (AppConstants.UPDATE_BUTTON.equalsIgnoreCase(buttonType)) {
						currentAddressIdText.setText(String.valueOf(address.getId()));
					}
					currentAddressText.setText(address.getAddress());
					currentAddressCityText.setText(address.getCity());
					currentAddressStateText.setText(address.getState());
					currentAddressCountryText.setText(address.getCountry());
					currentAddressPincodeText.setText(address.getPincode());
				}
			}
			phoneNumberText.setText(patientById.getPhoneNumber());
			secondaryPhoneNumberText.setText(
					patientById.getSecondaryPhoneNumber() != null ? patientById.getSecondaryPhoneNumber() : "");
			landLineNumberText.setText(patientById.getLandLineNumber() != null ? patientById.getLandLineNumber() : "");
		}

	}
}
