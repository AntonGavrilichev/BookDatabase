package ru.bookDB.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.bookDB.dao.PersonDao;
import ru.bookDB.model.Person;

@Component
public class PersonValidator implements Validator {

    public final PersonDao personDao;
    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    //В этом методе мы даем понять Спрингу к какому классу относится этот валидатор
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass); // Person.class должен равнятся классу который передан в качестве аргумента
                                            //Воозвращается true если классы совпадают
    }


    //Этот метод будет вызываться на том объекте, который приходит с форм в контроллере
    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o; //Делаем даункаст до Person. Так как мы знаем что в аргументы будет приходить Person

        //Проверяем, есть ли человек с таким же ФИО в БД
        if (personDao.getPersonByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
    }
}
