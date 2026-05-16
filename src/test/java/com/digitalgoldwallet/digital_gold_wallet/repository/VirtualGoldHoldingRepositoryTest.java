package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace=
                AutoConfigureTestDatabase.Replace.NONE
)
class VirtualGoldHoldingRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorBranchRepository vendorBranchRepository;

    @Autowired
    private VirtualGoldHoldingRepository
            virtualGoldHoldingRepository;


    @Test
    void shouldSaveVirtualHolding(){


        Address address=
                new Address();

        address.setStreet("Anna Nagar");
        address.setCity("Chennai");
        address.setState("Tamil Nadu");
        address.setPostalCode("600040");
        address.setCountry("India");

        Address savedAddress=
                addressRepository.save(address);



        User user=
                new User();

        user.setName("Mrugan");
        user.setEmail("mrugan@test.com");

        user.setAddress(savedAddress);

        User savedUser=
                userRepository.save(user);



        Vendor vendor=
                new Vendor();

        vendor.setVendorName("Tanishq");

        Vendor savedVendor=
                vendorRepository.save(vendor);



        VendorBranch branch=
                new VendorBranch();

        branch.setVendor(savedVendor);

        branch.setBranchName(
                "Chennai Branch"
        );

        branch.setAddress(
                savedAddress
        );

        VendorBranch savedBranch=
                vendorBranchRepository.save(
                        branch
                );



        VirtualGoldHolding holding=
                new VirtualGoldHolding();

        holding.setUser(
                savedUser
        );

        holding.setBranch(
                savedBranch
        );

        holding.setQuantity(
                new BigDecimal("15")
        );

        holding.setCreatedAt(
                LocalDateTime.now()
        );



        VirtualGoldHolding savedHolding=
                virtualGoldHoldingRepository
                        .save(
                                holding
                        );


        assertNotNull(
                savedHolding
                        .getHoldingId()
        );

        System.out.println(
                "TEST PASSED"
        );

        System.out.println(
                savedHolding
        );

    }

}