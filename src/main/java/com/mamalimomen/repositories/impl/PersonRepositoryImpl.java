package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.repositories.impl.BaseRepositoryImpl;
import com.mamalimomen.domains.Person;
import com.mamalimomen.repositories.PersonRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class PersonRepositoryImpl extends BaseRepositoryImpl<String, Person> implements PersonRepository {
    public PersonRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public List<Person> findAllPersons() {
        return findAllByNamedQuery("Person.findAll", Person.class);
    }

    @Override
    public List<Person> findManyPersonsByPhoneNumberLike(String phoneNumberLike) {
        return findManyByNativeQuery("{phone_number:{$regex:\"" + phoneNumberLike + "\",$options:\"$i\"}}", Person.class);
    }

    @Override
    public List<Person> findManyPersonsByFirstNameLike(String firstNameLike) {
        return findManyByNativeQuery("{first_name:{$regex:\"" + firstNameLike + "\",$options:\"$i\"}}", Person.class);
    }

    @Override
    public List<Person> findManyPersonsByLastNameLike(String lastNameLike) {
        return findManyByNativeQuery("{last_name:{$regex:\"" + lastNameLike + "\",$options:\"$i\"}}", Person.class);
    }

    @Override
    public List<Person> findManyPersonsByIdBoundary(Long minBound, Long maxBound) {
        return findManyByNativeQuery("{$and:[{entityId:{$gt:" + minBound + ",entityId:{$lt:" + maxBound + "}}]}", Person.class);
    }
}
