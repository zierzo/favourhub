package com.favour.dome.entity;

import javax.persistence.*;

/**
 * Created by fernando on 26/05/15.
 */
@Entity
public class ContactDetail {

    @Id
    @GeneratedValue
    @Column(name="ID", unique=true, nullable=false)
    private Long id;

    @Column(name="Contact", length=45, nullable=false)
    private String contact;

    @ManyToOne(optional=false)
    @JoinColumn(name="ContactTypeID", nullable=false)
    private ContactType type;

    @Column(name="Preferred", columnDefinition="BIT default b'0'", nullable=false)
    private boolean preferred=false;

    @Column(name="Active", columnDefinition="BIT default b'1'", nullable=false)
    private boolean active=true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}

/*
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Contact` varchar(45) NOT NULL,
  `ContactTypeID` int(11) NOT NULL,
  `Preferred` bit(1) DEFAULT b'0',
  `Active` bit(1) DEFAULT b'1',
  `CollaboratorID` bigint(20) DEFAULT NULL,
 */
