package com.ecommerce.project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min=5,message = "Street name should conatin 5 characters")
    private String street;

    @NotBlank
    @Size(min=5,message = "Building name should conatin 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min=5,message = "city name should conatin4 characters")
    private String city;

    @NotBlank
    @Size(min=5,message = "state name should conatin 2 characters")
    private String state;

    @NotBlank
    @Size(min=5,message = "country name should conatin 2 characters")
    private String country;

    @NotBlank
    @Size(min=5,message = "pincode name should conatin 6 characters")
    private String pincode;

    @ManyToMany(mappedBy = "addresses")
    private List<User>  users = new ArrayList<>();

    public Address(Long addressId, String street, String buildingName, String pincode, String country, String state, String city) {
        this.addressId = addressId;
        this.street = street;
        this.buildingName = buildingName;
        this.pincode = pincode;
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pincode='" + pincode + '\'' +
                ", users=" + users +
                '}';
    }
}
