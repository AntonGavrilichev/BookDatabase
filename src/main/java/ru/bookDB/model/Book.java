package ru.bookDB.model;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Year;

import static java.time.Year.*;


public class Book {


    private int id;
    @NotEmpty(message = "Заполните название книги")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов")
    private String title;
    @NotEmpty(message = "Укажите автора книги")
    @Size(min = 2, max = 100, message = "Имя автора должно быть от 2 до 100 символов")
    private String author;
    private String year;

    public Book() {}
    public Book(String title, String author, String year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }
}
