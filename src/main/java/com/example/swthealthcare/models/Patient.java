package com.example.swthealthcare.models;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Class declared for patients.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Patient {
	/**
	 * Patient Id.
	 */
	private Long id;
	/**
	 * Patient first name.
	 */
	private String firstName;
	/**
	 * Patient last name.
	 */
	private String lastName;
	/**
	 * Patient gender.
	 */
	private String gender;
	/**
	 * Patient date of birth.
	 */
	private String dateOfBirth;
	/**
	 * List of addresses.
	 */
	private List<Address> addresses;
	/**
	 * Patient phone number.
	 */
	private String phoneNumber;
	/**
	 * Patient secondary phone number.
	 */
	private String secondaryPhoneNumber;
	/**
	 * Patient landline number.
	 */
	private String landLineNumber;
}
