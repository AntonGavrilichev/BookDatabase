package ru.bookDB.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.bookDB.model.Book;
import ru.bookDB.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;
    private final PersonDao personDao;
    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate, PersonDao personDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDao = personDao;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    //Найдем человека по id и вернем его. Если не найдется - вернуть null.
    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save (Book book) {
        jdbcTemplate.update("INSERT INTO Book (author, title, year) VALUES (?, ?, ?)",
                book.getAuthor(),book.getTitle(), book.getYear());
    }

    public void update (int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET author = ?, title = ?, year = ? WHERE id = ?",
                book.getAuthor(), book.getTitle(), book.getYear(), id);
    }


    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    public Optional<Person> getBookOwner (int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id_person = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Book Set id_person = NULL WHERE id = ?", id);
    }

    public void assign(int id, Person person) {
        jdbcTemplate.update("UPDATE Book SET id_person = ? WHERE id = ?", person.getId(), id);
    }


}
