package com.favour.dome.entity;

import javax.persistence.*;

/**
 * Created by fernando on 26/05/15.
 */
@Entity
public class ContactType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique=true, nullable=false)
    private Integer id;

    @Column(name="Type", length=45, nullable=false)
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactType that = (ContactType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
