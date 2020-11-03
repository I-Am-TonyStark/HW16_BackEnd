package com.mamalimomen.repositories;

import com.mamalimomen.base.repositories.BaseRepository;
import com.mamalimomen.domains.Person;

import java.util.List;

public interface PersonRepository extends BaseRepository<String, Person> {
    List<Person> findAllPersons();

    List<Person> findManyPersonsByPhoneNumberLike(String phoneNumberLike);

    List<Person> findManyPersonsByFirstNameLike(String firstNameLike);

    List<Person> findManyPersonsByLastNameLike(String lastNameLike);

    List<Person> findManyPersonsByIdBoundary(Long minBound, Long maxBound);
}
