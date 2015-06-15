package com.favour.dome.entity;

import javax.persistence.*;

/**
 * Created by fernando on 26/05/15.
 */
@Entity
public class OfferedFavour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique=true, nullable=false)
    private Integer id;

    @Column(name="Favour", length=120, nullable=false)
    private String favour;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFavour() {
        return favour;
    }

    public void setFavour(String favour) {
        this.favour = favour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfferedFavour that = (OfferedFavour) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(favour != null ? !favour.equals(that.favour) : that.favour != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (favour != null ? favour.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OfferedFavour{" +
                "id=" + id +
                ", favour='" + favour + '\'' +
                '}';
    }
}
