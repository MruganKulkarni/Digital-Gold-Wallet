package com.digitalgoldwallet.digital_gold_wallet; // package declaration

import com.digitalgoldwallet.digital_gold_wallet.entity.Address; // importing Address entity
import com.digitalgoldwallet.digital_gold_wallet.entity.User; // importing User entity
import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository; // importing AddressRepository
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository; // importing UserRepository

import org.springframework.boot.CommandLineRunner; // runs code after Spring Boot starts
import org.springframework.boot.SpringApplication; // launches Spring Boot application
import org.springframework.boot.autoconfigure.SpringBootApplication; // marks this as Spring Boot application
import org.springframework.context.annotation.Bean; // used to create Spring beans

import java.math.BigDecimal; // used for wallet balance

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

			System.out.println("CREATE SUCCESS: User saved with ID = "
					+ user.getUserId());


			// ---------------- READ USER ----------------

			User foundUser = userRepository.findById(user.getUserId())
					.orElse(null);

			/*
			 * Checking whether user exists
			 */
			if (foundUser != null) {

				System.out.println("READ SUCCESS: User found = "
						+ foundUser.getName());

			} else {

				System.out.println("READ FAILED: User with ID "
						+ user.getUserId() + " is null");
			}


			// ---------------- UPDATE USER ----------------

			if (foundUser != null) {

				foundUser.setBalance(new BigDecimal("10000.00"));

				// save updated user
				userRepository.save(foundUser);

				System.out.println("UPDATE SUCCESS: User balance updated to = "
						+ foundUser.getBalance());

			} else {

				System.out.println("UPDATE FAILED: User object is null");
			}


			// ---------------- CUSTOM QUERY TEST ----------------

			System.out.println("CUSTOM QUERY TEST: Users with balance > 1000");

			userRepository.findUsersWithBalanceGreaterThan(
							new BigDecimal("1000"))
					.forEach(u -> {

						if (u != null) {

							System.out.println("CUSTOM QUERY SUCCESS: User Found = "
									+ u.getName());

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

				boolean exists = userRepository.existsById(
						foundUser.getUserId());

				System.out.println("VERIFY DELETE: User exists after delete = "
						+ exists);

			} else {

				System.out.println("VERIFY FAILED: Cannot verify null user");
			}
		};
	}
}