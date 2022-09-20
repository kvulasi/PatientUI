package com.example.swthealthcare;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.swthealthcare.ui.TableUI;
/**
 * Class declared for spring boot main method.
 */
@SpringBootApplication
public class SWTHealthCareApplication {
	/**
	 * Main method.
	 * 
	 * @param args {@link Array} of {@link String}
	 */
	public static void main(String[] args) {
		TableUI tableUI = new TableUI();
		try {
			tableUI.tableUI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
