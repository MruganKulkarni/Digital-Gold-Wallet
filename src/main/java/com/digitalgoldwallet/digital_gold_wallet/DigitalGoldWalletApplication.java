package com.digitalgoldwallet.digital_gold_wallet; // package declaration

import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor; // importing Vendor entity
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorRepository; // importing VendorRepository
import org.springframework.boot.CommandLineRunner; // runs code after Spring Boot starts
import org.springframework.boot.SpringApplication; // used to launch Spring Boot app
import org.springframework.boot.autoconfigure.SpringBootApplication; // marks this as Spring Boot main class
import org.springframework.context.annotation.Bean; // marks method as a Spring bean

import java.math.BigDecimal; // used for gold price and quantity

@SpringBootApplication // enables auto-configuration, component scan, etc
public class DigitalGoldWalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalGoldWalletApplication.class, args); // launches the application
	}

	@Bean // tells Spring to manage this as a bean
	public CommandLineRunner testVendorCRUD(VendorRepository vendorRepository) {
		// CommandLineRunner runs this code automatically after app starts
		return args -> {

			// ---- CREATE ----
			Vendor vendor = new Vendor(); // creating new Vendor object
			vendor.setVendorName("Test Gold Bro"); // setting vendor name
			vendor.setDescription("Testing CRUD for Day 2"); // setting description
			vendor.setContactPersonName("Sparsh Garg"); // setting contact person
			vendor.setContactEmail("sparsh@test.com"); // setting contact email
			vendor.setContactPhone("9999999999"); // setting phone number
			vendor.setTotalGoldQuantity(new BigDecimal("500.00")); // setting gold quantity
			vendor.setCurrentGoldPrice(new BigDecimal("6400.00")); // setting gold price
			vendorRepository.save(vendor); // saves vendor to DB
			System.out.println("✅ CREATE: Vendor saved with ID = " + vendor.getVendorId());

			// ---- READ ----
			Vendor found = vendorRepository.findById(vendor.getVendorId()).orElse(null);
			// fetches vendor from DB by ID
			System.out.println("✅ READ: Vendor found = " + found.getVendorName());

			// ---- UPDATE ----
			found.setCurrentGoldPrice(new BigDecimal("6500.00")); // updating gold price
			vendorRepository.save(found); // saves updated vendor back to DB
			System.out.println("✅ UPDATE: Gold price updated to = " + found.getCurrentGoldPrice());

			// ---- DELETE ----
			vendorRepository.deleteById(found.getVendorId()); // deletes vendor from DB by ID
			System.out.println("✅ DELETE: Vendor deleted successfully");

			// ---- VERIFY ----
			boolean exists = vendorRepository.existsById(found.getVendorId());
			// checks if vendor still exists after deletion
			System.out.println("✅ VERIFY: Vendor exists after delete = " + exists); // should print false
		};
	}
}
