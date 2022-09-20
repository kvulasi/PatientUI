package com.example.swthealthcare.httpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.example.swthealthcare.constants.AppConstants;
import com.example.swthealthcare.models.Patient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Class declared to consume data from restapi's using HttpClient.
 */
public class PatientHttpClient {
	/**
	 * HttpRequest variable..
	 */
	HttpRequest request = null;
	/**
	 * URI variable..
	 */
	URI uri = null;
	/**
	 * HttpResponse variable.
	 */
	HttpResponse<String> response = null;
	/**
	 * Method defined to consume all the patients.
	 * @return {@link List} of {@link Patient}
	 * @throws {@link IOException}HttpRequest request = HttpRequest.newBuilder().uri(URI.create(patientURI + "/")).GET().build();
	 * @throws {@link InterruptedException}
	 */
	public List<Patient> getAllPatients() throws IOException, InterruptedException {
		request = HttpRequest.newBuilder().uri(URI.create(AppConstants.PATIENT_URI + "/")).GET().build();
		response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		if (!response.body().isEmpty()) {
			return new ObjectMapper().readValue(response.body(), new TypeReference<List<Patient>>() {
			});
		} else {
			return new ArrayList<Patient>();
		}
	}
	/**
	 * Method defined to consume patient by given patientId.
	 * @param patientId {@link Long}
	 * @return {@link List} of {@link Patient}
	 * @throws {@link IOException}
	 * @throws {@link InterruptedException}
	 */
	public Patient getPatientById(Long patientId) throws IOException, InterruptedException {
		request = HttpRequest.newBuilder().uri(URI.create(AppConstants.PATIENT_URI + "/" + patientId)).GET().build();
		response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		return new ObjectMapper().readValue(response.body(), Patient.class);
	}
	/**
	 * Method defined to consume patient by given search keyword.
	 * @param searchKeyword {@link String}
	 * @return {@link List} of {@link Patient}
	 * @throws {@link IOException}
	 * @throws {@link InterruptedException}
	 */
	public List<Patient> getPatientsBySearch(String searchKeyword) throws IOException, InterruptedException {
		request = HttpRequest.newBuilder().uri(URI.create(AppConstants.PATIENT_URI + "?search=" + searchKeyword)).GET().build();
		response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		return new ObjectMapper().readValue(response.body(), new TypeReference<List<Patient>>() {
		});
	}
	/**
	 * Method defined to add patient.
	 * @param patient {@link Patient}
	 * @return {@link Patient}
	 * @throws {@link IOException}
	 * @throws {@link InterruptedException}
	 */
	public Patient addPatient(Patient patient) throws IOException, InterruptedException {
		request = HttpRequest.newBuilder().uri(URI.create(AppConstants.PATIENT_URI + "/" + AppConstants.CREATE))
				.POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(patient)))
				.setHeader("Content-Type", "application/json").build();

		response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		return new ObjectMapper().readValue(response.body(), Patient.class);
	}
	/**
	 * Method defined to update patient.
	 * @param patientId {@link Long}
	 * @param patient {@link Patient}
	 * @return {@link int}
	 * @throws {@link IOException}
	 * @throws {@link InterruptedException}
	 */
	public int updatePatient(Long patientId, Patient patient) throws IOException, InterruptedException {
		request = HttpRequest.newBuilder().uri(URI.create(AppConstants.PATIENT_URI + "/" + patientId))
				.PUT(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(patient)))
				.setHeader("Content-Type", "application/json").build();

		response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		return response.statusCode();
	}
	/**
	 * Method defined to delete patient with the given patientId.
	 * @param patientId {@link Long}
	 * @return {@link int}
	 * @throws {@link IOException}
	 * @throws {@link InterruptedException}
	 */
	public int deletePatientById(Long patientId) throws IOException, InterruptedException {
		request = HttpRequest.newBuilder().uri(URI.create(AppConstants.PATIENT_URI + "/" + patientId)).DELETE().build();
		response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		return response.statusCode();
	}
}
