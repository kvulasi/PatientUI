package com.example.swthealthcare.patientui;

import java.io.IOException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.swthealthcare.constants.AppConstants;

public class PatientUITest {
	@InjectMocks
	private PatientUI patientUI;
	
	@Mock
	private Table table;
	
	@Mock
	private Display display;
	
	@Test
	public void patientCreateView() throws IOException, InterruptedException {
		PatientUI patientUI = new PatientUI();
		patientUI.patientViewFill(table, display, AppConstants.PATIENT_UPDATE, AppConstants.CREATE_BUTTON, 27L);
	}
	
//	@Test
//	public void patientView() throws IOException, InterruptedException {
//		PatientUI patientUI = new PatientUI();
//		patientUI.patientViewFill(table, display, AppConstants.PATIENT_VIEW, AppConstants.VIEW_BUTTON, 27L);
//	}
}
