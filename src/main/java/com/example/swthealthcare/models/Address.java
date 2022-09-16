package com.example.swthealthcare.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class declared for patients addresses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
	/**
	 * Address Id.
	 */
	private Long id;
	/**
	 * Patient Id.
	 */
	private Long patientId;
	/**
	 * Address type.
	 */
	private String addressType;
	/**
	 * Address.
	 */
	private String address;
	/**
	 * City.
	 */
	private String city;
	/**
	 * State.
	 */
	private String state;
	/**
	 * Country.
	 */
	private String country;
	/**
	 * Pincode.
	 */
	private String pincode;
}
