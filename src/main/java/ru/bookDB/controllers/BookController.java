package ru.bookDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bookDB.dao.BookDao;
import ru.bookDB.dao.PersonDao;
import ru.bookDB.model.Book;
import ru.bookDB.model.Person;
import ru.bookDB.util.PersonValidator;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BookController {
    private final PersonDao personDao;
    private final BookDao bookDao;
    @Autowired
    public BookController(PersonDao personDao, PersonValidator personValidator, BookDao bookDao) {
        this.personDao = personDao;
        this.bookDao = bookDao;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model, @ModelAttribute("person") Person person) {

        model.addAttribute("book", bookDao.show(id));
        Optional<Person> bookOwner = bookDao.getBookOwner(id);
        if(bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDao.index());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "books/new";

        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update (@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult, @PathVariable("id") int id) {
        if(bindingResult.hasErrors())
            return "books/edit";

        bookDao.update(id, book);
        return "redirect:/books";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDao.release(id);
        return "redirect:/books/" + id;
    }












}
