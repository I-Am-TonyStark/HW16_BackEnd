package com.mamalimomen.services.impl;

import com.mamalimomen.base.controllers.utilities.InValidDataException;
import com.mamalimomen.base.services.impl.BaseServiceImpl;
import com.mamalimomen.domains.Person;
import com.mamalimomen.repositories.PersonRepository;
import com.mamalimomen.repositories.impl.PersonRepositoryImpl;
import com.mamalimomen.services.PersonService;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PersonServiceImpl extends BaseServiceImpl<String, Person, PersonRepository> implements PersonService {
    public PersonServiceImpl(EntityManager em) {
        super(new PersonRepositoryImpl(em));
    }

    @Override
    public String createNewPerson(HttpServletRequest req) {
        Person person = new Person();

        try {
            person.setFirstName(req.getParameter("first_name"));
            person.setLastName(req.getParameter("last_name"));
            person.setPhoneNumber(req.getParameter("phone_number"));

            if (repository.saveOne(person)) {
                return "Your Person was created successfully!";
            } else return "There is a problem at persisting Person";
        } catch (InValidDataException e) {
            return "Wrong entered data format for " + e.getMessage() + "!";
        }
    }

    @Override
    public List<Person> retrieveExistPersons(HttpServletRequest req) {
        String searchKey = req.getParameter("information");
        String parameter = req.getParameter("search");

        if (parameter != null) {
            return switch (parameter) {
                case "fName_like" -> repository.findManyPersonsByFirstNameLike(searchKey);
                case "lName_like" -> repository.findManyPersonsByLastNameLike(searchKey);
                case "phone_like" -> repository.findManyPersonsByPhoneNumberLike(searchKey);
                default -> null;
            };
        }
        return null;
    }
}
