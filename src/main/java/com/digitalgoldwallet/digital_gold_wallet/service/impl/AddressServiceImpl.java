package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.exception.AddressNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;
import com.digitalgoldwallet.digital_gold_wallet.service.AddressService;

import org.springframework.stereotype.Service;

/*
 * Address Service Implementation
 */

@Service
public class AddressServiceImpl implements AddressService {

    /*
     * Repository object
     */
    private final AddressRepository addressRepository;

    /*
     * Constructor Injection
     */
    public AddressServiceImpl(AddressRepository addressRepository) {

        this.addressRepository = addressRepository;
    }

    /*
     * CREATE ADDRESS
     */
    @Override
    public Address createAddress(Address address) {

        return addressRepository.save(address);
    }

    /*
     * GET ADDRESS BY ID
     */
    @Override
    public Address getAddressById(Integer addressId) {

        return addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new AddressNotFoundException(
                                "Address not found with ID: " + addressId
                        ));
    }

    /*
     * UPDATE ADDRESS
     */
    @Override
    public Address updateAddress(Integer addressId,
                                 Address updatedAddress) {

        Address existingAddress =
                getAddressById(addressId);

        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());
        existingAddress.setCountry(updatedAddress.getCountry());

        return addressRepository.save(existingAddress);
    }
}