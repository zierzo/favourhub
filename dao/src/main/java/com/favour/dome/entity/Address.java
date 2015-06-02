package com.favour.dome.entity;

import javax.persistence.*;

/**
 * Created by fernando on 26/05/15.
 */
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique=true, nullable=false)
    private Integer id;

    @Column(name="City", length=45, nullable=false)
    private String city;

    @ManyToOne(optional=false)
    @JoinColumn(name="CountryID", nullable=false)
    private Country country;

    @Column(name="Address", length=120)
    private String address;

    @Column(name="ZipCode", length=45)
    private String zipCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address1 = (Address) o;

        if (id != null ? !id.equals(address1.id) : address1.id != null) return false;
        if (city != null ? !city.equals(address1.city) : address1.city != null) return false;
        if (address != null ? !address.equals(address1.address) : address1.address != null) return false;
        return !(zipCode != null ? !zipCode.equals(address1.zipCode) : address1.zipCode != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        return result;
    }
}

