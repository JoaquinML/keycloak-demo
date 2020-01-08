package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.demo.model.Book;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository{
    private static Map<String, Book> books = new ConcurrentHashMap<>();

    static {
        books.put("B01", new Book("B01", "Harry Potter and the Deathly Hallows", "J.K. Rowling"));
        books.put("B02", new Book("B02", "The Lord of the Rings", "J.R.R. Tolkien"));
        books.put("B03", new Book("B03", "War and Peace", "Leo Tolstoy"));
    }

    public List<Book> readAll() {
        List<Book> allBooks = new ArrayList<>(books.values());
        allBooks.sort(Comparator.comparing(Book::getId));
        return allBooks;
    }
}