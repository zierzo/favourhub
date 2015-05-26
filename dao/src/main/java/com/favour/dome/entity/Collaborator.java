package com.favour.dome.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by fernando on 26/05/15.
 */
@Entity
public class Collaborator {

    @Id
    @GeneratedValue
    @Column(name="ID", unique=true, nullable=false)
    private Long id;

    @Column(name="Email", length=45, unique=true, nullable=false)
    private String email;

    @Column(name="Password", length=45, nullable=false)
    private String password;

    @Column(name="FirstName", length=45, nullable=false)
    private String firstName;

    @Column(name="LastName", length=45, nullable=false)
    private String lastName;

    @Column(name="NickName", length=45)
    private String nickName;

    @OneToOne(optional=false)
    @MapsId
    private Address address;

    @OneToMany
    @JoinColumn(name="CollaboratorID", nullable=false)
    private Set<ContactDetail> contactDetails;

    @OneToMany
    @JoinColumn(name="CollaboratorID", nullable=false)
    private Set<OfferedFavour> offeredFavours;

    @Temporal(TemporalType.DATE)
    @Column(name="CreationDate", nullable=false)
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @Column(name="LastModifiedAt", nullable=false)
    private Date lastModifiedAt;

    @Column(name="Active", columnDefinition="BIT default b'0'", nullable=false)
    private Boolean active=true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<ContactDetail> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(Set<ContactDetail> contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Set<OfferedFavour> getOfferedFavours() {
        return offeredFavours;
    }

    public void setOfferedFavours(Set<OfferedFavour> offeredFavours) {
        this.offeredFavours = offeredFavours;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Collaborator that = (Collaborator) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) return false;
        return active.equals(that.active);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + active.hashCode();
        return result;
    }
}


