package com.mamalimomen.domains;


import com.mamalimomen.base.controllers.annotations.Column;
import com.mamalimomen.base.controllers.utilities.InValidDataException;
import com.mamalimomen.base.domains.BaseEntity;
import com.mamalimomen.base.controllers.enumerations.CellType;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@SelectBeforeUpdate
@Table(name = "tbl_person", catalog = "HW16_One", schema = "HW16_One")
@NamedQueries({
        @NamedQuery(
                name = "Person.findAll",
                query = "SELECT p FROM Person p"),
        @NamedQuery(
                name = "Person.findManyByIdBoundary",
                query = "SELECT p FROM Person p WHERE p.entityId BETWEEN ?1 AND ?2"),
        @NamedQuery(
                name = "Person.findManyByFirstNameLike",
                query = "SELECT p FROM Person p WHERE p.firstName LIKE ?1"),
        @NamedQuery(
                name = "Person.findManyByLastNameLike",
                query = "SELECT p FROM Person p WHERE p.lastName LIKE ?1"),
        @NamedQuery(
                name = "Person.findManyByPhoneNumberLike",
                query = "SELECT p FROM Person p WHERE p.phoneNumber LIKE ?1")
})
public class Person extends BaseEntity implements Comparable<Person> {

    @Column(index = 0, type = CellType.NUMERIC)
    private Long entityId;

    @javax.persistence.Column(name = "first_name", nullable = false)
    @Column(index = 1, type = CellType.STRING)
    private String firstName;

    @javax.persistence.Column(name = "last_name", nullable = false)
    @Column(index = 2, type = CellType.STRING)
    private String lastName;

    @javax.persistence.Column(name = "phone_number", nullable = false)
    @Column(index = 3, type = CellType.STRING)
    private String phoneNumber;

    public Person(){
        this.entityId = getCount();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws InValidDataException {
        if (!firstName.matches("(\\w\\s?){3,}")) {
            throw new InValidDataException("FirstName");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InValidDataException {
        if (!lastName.matches("(\\w\\s?){3,}")) {
            throw new InValidDataException("LastName");
        }
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("%s<br/>%s<br/>%s<br/>%s<br/>", getId(), getFirstName(), getLastName(), getPhoneNumber());
    }

    @Override
    public int compareTo(Person p) {
        return this.getId().compareTo(p.getId());
    }


    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return this.getEntityId().intValue();
    }
}
