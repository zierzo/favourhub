package com.favour.dome.entity;

import javax.persistence.*;

/**
 * Created by fernando on 26/05/15.
 */
@Entity
public class ContactDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique=true, nullable=false)
    private Integer id;

    @Column(name="Contact", length=45, nullable=false)
    private String contact;

    @ManyToOne(optional=false)
    @JoinColumn(name="ContactTypeID", nullable=false)
    private ContactType type;

    @Column(name="Preferred", columnDefinition="BIT default b'0'", nullable=false)
    private boolean preferred=false;

    @Column(name="Active", columnDefinition="BIT default b'1'", nullable=false)
    private boolean active=true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactDetail that = (ContactDetail) o;

        if (preferred != that.preferred) return false;
        if (active != that.active) return false;
        if (!id.equals(that.id)) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (preferred ? 1 : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactDetail{" +
                "id=" + id +
                ", contact='" + contact + '\'' +
                ", type=" + type +
                ", preferred=" + preferred +
                ", active=" + active +
                '}';
    }
}

