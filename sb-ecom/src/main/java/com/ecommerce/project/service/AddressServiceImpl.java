package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {

        Address address  = modelMapper.map(addressDTO,Address.class);
        List<Address> addressList = user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);

        address.setUsers(user);
        Address savedAddress  = addressRepository.save(address);
        return modelMapper.map(savedAddress,AddressDTO.class);

    }

    @Override
    public List<AddressDTO> getAddresses() {
        {
            List<Address> addresses = addressRepository.findAll();
            if(addresses.isEmpty()){
                throw new APIException("No address exists");
            }

            return addresses.stream().map(
                    address -> modelMapper.map(address,AddressDTO.class))
                    .toList();

        }
    }

    @Override
    public AddressDTO getAddressesById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address","addressId",addressId) );
        return modelMapper.map(address,AddressDTO.class);

    }

    @Override
    public List<AddressDTO> getUserAddresses(User user) {

        List<Address> addresses = user.getAddresses();
        if(addresses.isEmpty()){
            throw new APIException("No address exists for user");
        }
        return addresses.stream().map(address -> modelMapper.map(address,AddressDTO.class)).collect(Collectors.toList());

    }@Autowired
    UserRepository userRepository;

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {

        Address addressfromdatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address","addressId",addressId) );
        addressfromdatabase.setCity(addressDTO.getCity());
        addressfromdatabase.setState(addressDTO.getState());
        addressfromdatabase.setCountry(addressDTO.getCountry());
        addressfromdatabase.setPincode(addressDTO.getPincode());
        addressfromdatabase.setStreet(addressDTO.getStreet());
        addressfromdatabase.setBuildingName(addressDTO.getBuildingName());
        Address savedAddress = addressRepository.save(addressfromdatabase);
        User user = addressfromdatabase.getUsers();
        user.getAddresses().removeIf(address->address.getAddressId().equals(addressId));
        user.getAddresses().add(savedAddress);
        userRepository.save(user);

        return modelMapper.map(savedAddress,AddressDTO.class);

    }

    @Override
    public String deleteAddress(Long addressId) {
        Address addressfromdatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address","addressId",addressId) );

        User user = addressfromdatabase.getUsers();
        user.getAddresses().removeIf(address->address.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(addressfromdatabase);

        return "Address deleted successfully"+addressId;
    }
}
