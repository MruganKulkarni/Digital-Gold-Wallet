package com.digitalgoldwallet.digital_gold_wallet; // package declaration

import com.digitalgoldwallet.digital_gold_wallet.entity.Address; // importing Address entity
import com.digitalgoldwallet.digital_gold_wallet.entity.User; // importing User entity
import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor; // importing Vendor entity
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch; // importing VendorBranch entity
import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository; // importing AddressRepository
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository; // importing UserRepository
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorRepository; // importing VendorRepository
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository; // importing VendorBranchRepository

import org.springframework.boot.CommandLineRunner; // runs code after Spring Boot starts
import org.springframework.boot.SpringApplication; // launches Spring Boot application
import org.springframework.boot.autoconfigure.SpringBootApplication; // marks this as Spring Boot application
import org.springframework.context.annotation.Bean; // used to create Spring beans

import java.math.BigDecimal; // used for wallet balance and gold quantity

@SpringBootApplication
public class DigitalGoldWalletApplication {

	public static void main(String[] args) {
		// starts Spring Boot application
		SpringApplication.run(DigitalGoldWalletApplication.class, args);
	}

	@Bean
	public CommandLineRunner testUserCRUD(UserRepository userRepository,
	                                      AddressRepository addressRepository) {
		// runs automatically after application starts
		return args -> {

			// ---------------- CREATE ADDRESS ----------------
			Address address = new Address();
			address.setStreet("Anna Nagar");
			address.setCity("Chennai");
			address.setState("Tamil Nadu");
			address.setPostalCode("600040");
			address.setCountry("India");

			// save address into database
			addressRepository.save(address);
			System.out.println("CREATE SUCCESS: Address saved successfully");

			// ---------------- CREATE USER ----------------
			User user = new User();
			user.setName("Varsha Karthikeyan");
			user.setEmail("varsha@test.com");
			user.setBalance(new BigDecimal("5000.00"));
			user.setAddress(address);

			// save user into database
			userRepository.save(user);
			System.out.println("CREATE SUCCESS: User saved with ID = " + user.getUserId());

			// ---------------- READ USER ----------------
			User foundUser = userRepository.findById(user.getUserId()).orElse(null);

			if (foundUser != null) {
				System.out.println("READ SUCCESS: User found = " + foundUser.getName());
			} else {
				System.out.println("READ FAILED: User with ID " + user.getUserId() + " is null");
			}

			// ---------------- UPDATE USER ----------------
			if (foundUser != null) {
				foundUser.setBalance(new BigDecimal("10000.00"));
				userRepository.save(foundUser); // save updated user
				System.out.println("UPDATE SUCCESS: User balance updated to = " + foundUser.getBalance());
			} else {
				System.out.println("UPDATE FAILED: User object is null");
			}

			// ---------------- CUSTOM QUERY TEST ----------------
			System.out.println("CUSTOM QUERY TEST: Users with balance > 1000");
			userRepository.findUsersWithBalanceGreaterThan(new BigDecimal("1000"))
					.forEach(u -> {
						if (u != null) {
							System.out.println("CUSTOM QUERY SUCCESS: User Found = " + u.getName());
						} else {
							System.out.println("CUSTOM QUERY FAILED: User is null");
						}
					});

			// ---------------- DELETE USER ----------------
			if (foundUser != null) {
				userRepository.deleteById(foundUser.getUserId());
				System.out.println("DELETE SUCCESS: User deleted successfully");
			} else {
				System.out.println("DELETE FAILED: User object is null");
			}

			// ---------------- VERIFY DELETE ----------------
			if (foundUser != null) {
				boolean exists = userRepository.existsById(foundUser.getUserId());
				System.out.println("VERIFY DELETE: User exists after delete = " + exists);
			} else {
				System.out.println("VERIFY FAILED: Cannot verify null user");
			}
		};
	}

	@Bean
	public CommandLineRunner testVendorCRUD(VendorRepository vendorRepository,
	                                        VendorBranchRepository vendorBranchRepository,
	                                        AddressRepository addressRepository) {
		// runs automatically after application starts — tests Sparsh's Vendor module
		return args -> {

			// ---------------- CREATE VENDOR ----------------
			Vendor vendor = new Vendor(); // creating new Vendor object
			vendor.setVendorName("Sparsh Gold Traders"); // setting vendor name
			vendor.setDescription("Premium gold trading company"); // setting description
			vendor.setContactPersonName("Sparsh Garg"); // setting contact person
			vendor.setContactEmail("sparsh@goldtraders.com"); // setting contact email
			vendor.setContactPhone("9999999999"); // setting phone number
			vendor.setTotalGoldQuantity(new BigDecimal("1000.00")); // setting total gold quantity
			vendor.setCurrentGoldPrice(new BigDecimal("6400.00")); // setting gold price per gram

			// save vendor into database
			vendorRepository.save(vendor);
			System.out.println("VENDOR CREATE SUCCESS: Vendor saved with ID = " + vendor.getVendorId());

			// ---------------- READ VENDOR ----------------
			Vendor foundVendor = vendorRepository.findById(vendor.getVendorId()).orElse(null);

			if (foundVendor != null) {
				System.out.println("VENDOR READ SUCCESS: Vendor found = " + foundVendor.getVendorName());
			} else {
				System.out.println("VENDOR READ FAILED: Vendor is null");
			}

			// ---------------- UPDATE VENDOR ----------------
			if (foundVendor != null) {
				foundVendor.setCurrentGoldPrice(new BigDecimal("6500.00")); // updating gold price
				vendorRepository.save(foundVendor); // save updated vendor
				System.out.println("VENDOR UPDATE SUCCESS: Gold price updated to = " + foundVendor.getCurrentGoldPrice());
			} else {
				System.out.println("VENDOR UPDATE FAILED: Vendor object is null");
			}

			// ---------------- CREATE VENDOR BRANCH ----------------
			Address branchAddress = new Address(); // creating address for branch
			branchAddress.setStreet("MG Road"); // setting street
			branchAddress.setCity("Bangalore"); // setting city
			branchAddress.setState("Karnataka"); // setting state
			branchAddress.setPostalCode("560001"); // setting postal code
			branchAddress.setCountry("India"); // setting country
			addressRepository.save(branchAddress); // save branch address

			VendorBranch branch = new VendorBranch(); // creating new VendorBranch object
			branch.setVendor(foundVendor); // linking branch to vendor
			branch.setAddress(branchAddress); // linking branch to address
			branch.setQuantity(new BigDecimal("500.00")); // setting gold quantity at branch

			// save branch into database
			vendorBranchRepository.save(branch);
			System.out.println("BRANCH CREATE SUCCESS: Branch saved with ID = " + branch.getBranchId());

			// ---------------- READ BRANCH ----------------
			VendorBranch foundBranch = vendorBranchRepository.findById(branch.getBranchId()).orElse(null);

			if (foundBranch != null) {
				System.out.println("BRANCH READ SUCCESS: Branch found with quantity = " + foundBranch.getQuantity());
			} else {
				System.out.println("BRANCH READ FAILED: Branch is null");
			}

			// ---------------- CUSTOM QUERY TEST ----------------
			System.out.println("CUSTOM QUERY TEST: Get gold price for vendor");
			BigDecimal price = vendorRepository.findGoldPriceByVendorId(foundVendor.getVendorId()); // fetch gold price
			System.out.println("CUSTOM QUERY SUCCESS: Gold price = " + price);

			// ---------------- DELETE BRANCH ----------------
			if (foundBranch != null) {
				vendorBranchRepository.deleteById(foundBranch.getBranchId()); // delete branch
				System.out.println("BRANCH DELETE SUCCESS: Branch deleted successfully");
			} else {
				System.out.println("BRANCH DELETE FAILED: Branch object is null");
			}

			// ---------------- DELETE VENDOR ----------------
			if (foundVendor != null) {
				vendorRepository.deleteById(foundVendor.getVendorId()); // delete vendor
				System.out.println("VENDOR DELETE SUCCESS: Vendor deleted successfully");
			} else {
				System.out.println("VENDOR DELETE FAILED: Vendor object is null");
			}

			// ---------------- VERIFY DELETE ----------------
			if (foundVendor != null) {
				boolean exists = vendorRepository.existsById(foundVendor.getVendorId()); // check if vendor still exists
				System.out.println("VENDOR VERIFY DELETE: Vendor exists after delete = " + exists); // should print false
			} else {
				System.out.println("VENDOR VERIFY FAILED: Cannot verify null vendor");
			}
		};
	}
}