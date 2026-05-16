package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;

public interface AddressService {

    Address createAddress(Address address);

    Address getAddressById(Integer addressId);

    Address updateAddress(Integer addressId, Address address);
}