package com.example.swthealthcare.httpclient;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutionException;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.example.swthealthcare.models.Address;
import com.example.swthealthcare.models.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class PatientHttpClientTest {

	@InjectMocks
	private PatientHttpClient patientHttpClient;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private HttpRequest request;
	
	@Mock
	private HttpResponse<String> response;
	
	@Mock
	private HttpRequest.Builder builder;
	 
	@Mock
	private BodyHandler<String> str;
	
	@Mock
	HttpClient httpClient = HttpClient.newHttpClient();
	 
	@Mock
	HttpRequest httpRequest;

	private List<Patient> getpatients() {
		List<Patient> patients = new ArrayList<Patient>();

		Patient patient1 = new Patient();
		patient1.setId(28L);
		patient1.setFirstName("Kousalya");
		patient1.setLastName("V");
		patient1.setGender("Female");
		patient1.setDateOfBirth("1989-05-10");
		patient1.setPhoneNumber("0123456789");
		patient1.setSecondaryPhoneNumber(null);
		patient1.setLandLineNumber(null);

		List<Address> addresses = new ArrayList<>();
		Address address1 = new Address();
		address1.setId(55L);
		address1.setPatientId(28L);
		address1.setAddressType("Permanent Address");
		address1.setAddress("Bangalore");
		address1.setCity("Bangalore");
		address1.setState("Karnataka");
		address1.setCountry("India");
		address1.setPincode("560078");

		Address address2 = new Address(56L, 28L, "Current Address", "Bangalore", "Punganur", "Karnataka", "India",
				"517247");

		addresses.add(address1);
		addresses.add(address2);

		patient1.setAddresses(addresses);

		Patient patient2 = new Patient(28L, "Kousalya", "V", "Female", "1989-05-10", addresses, "1122334455", null,
				null);
		patients.add(patient1);
		patients.add(patient2);

		return patients;
	}

	@Test
	public void getAllPatientsWithNotEmptyList() throws IOException, InterruptedException, URISyntaxException, ExecutionException, TimeoutException {
		List<Patient> patientsList = patientHttpClient.getAllPatients();
		assertThat(patientsList.size()>0);
	}
	
	@Test
	public void getPatientById() throws IOException, InterruptedException {
		Patient patient = patientHttpClient.getPatientById(28L);
		assertThat(patient.toString() != null);
	}
	
	
	@Test
	public void getPatientsBySearch() throws IOException, InterruptedException {
		List<Patient> patientsList = patientHttpClient.getPatientsBySearch("kou");
		assertThat(patientsList.size());
	}
	
	@Test
	public void updatePatient() throws IOException, InterruptedException {
		int statuccode = patientHttpClient.updatePatient(28L, getpatients().get(0));
		assertThat(statuccode>0);
	}
	
	@Test
	public void addPatient() throws IOException, InterruptedException {
		Patient patient = getpatients().get(0);
		patient.setId(null);
		patient.getAddresses().get(0).setId(null);
		patient.getAddresses().get(1).setId(null);
		Patient addPatient = patientHttpClient.addPatient(patient);
		assertThat(addPatient != null);
	}
	
	@Test
	public void deletePatient() throws IOException, InterruptedException {
		
		int addPatient = patientHttpClient.deletePatientById(31L);
		assertThat(addPatient==1);
	}
}
