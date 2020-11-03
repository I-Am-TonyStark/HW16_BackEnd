package com.mamalimomen.services;

import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.domains.Person;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PersonService extends BaseService<String, Person> {
    String createNewPerson(HttpServletRequest req);

    List<Person> retrieveExistPersons(HttpServletRequest req);
}
