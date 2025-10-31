package com.librarytest.librarytest.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.librarytest.librarytest.Models.Book;

public class InMemoryBookRepository {
    private final Map<Long,Book> books = new HashMap<>();

    public Book findById(Long id){
        return Optional.ofNullable(books.get(id)).get();
    }
    public InMemoryBookRepository(){
        books.put(1L, new Book(1L, "Book One", "Synopsis of Book One", 120,LocalDateTime.of(2020,3,10,0,0),"Author 1" ));
        books.put(2L, new Book(2L, "Book Two", "Synopsis of Book Two", 120,LocalDateTime.of(2020,3,10,0,0),"Author 2" ));
        books.put(3L, new Book(3L, "Book Three", "Synopsis of Book Three", 120,LocalDateTime.of(2020,3,10,0,0),"Author 3" ));
        books.put(4L, new Book(4L, "Book Four", "Synopsis of Book Four", 120,LocalDateTime.of(2020,3,10,0,0),"Author 4" ));
        books.put(5L, new Book(5L, "Book Five", "Synopsis of Book Five", 120,LocalDateTime.of(2020,3,10,0,0),"Author 5" ));
        books.put(6L, new Book(6L, "Book Six", "Synopsis of Book Six", 120,LocalDateTime.of(2020,3,10,0,0),"Author 6" ));
        books.put(7L, new Book(7L, "Book Seven", "Synopsis of Book Seven", 120,LocalDateTime.of(2020,3,10,0,0),"Author 7" ));
        books.put(8L, new Book(8L, "Book Eight", "Synopsis of Book Eight", 120,LocalDateTime.of(2020,3,10,0,0),"Author 8" ));
        books.put(9L, new Book(9L, "Book Nine", "Synopsis of Book Nine", 120,LocalDateTime.of(2020,3,10,0,0),"Author 9" ));
        books.put(10L, new Book(10L, "Book Ten", "Synopsis of Book Ten", 120,LocalDateTime.of(2020,3,10,0,0),"Author 10" ));
        books.put(11L, new Book(11L, "Book Eleven", "Synopsis of Book Eleven", 120,LocalDateTime.of(2020,3,10,0,0),"Author 11" ));
        books.put(12L, new Book(12L, "Book Twelve", "Synopsis of Book Twelve", 120,LocalDateTime.of(2020,3,10,0,0),"Author 12" ));
        books.put(13L, new Book(13L, "Book Thirteen", "Synopsis of Book Thirteen", 120,LocalDateTime.of(2020,3,10,0,0),"Author 13" ));
        books.put(14L, new Book(14L, "Book Fourteen", "Synopsis of Book Fourteen", 120,LocalDateTime.of(2020,3,10,0,0),"Author 14" ));
        books.put(15L, new Book(15L, "Book Fifiteen", "Synopsis of Book Fifiteen", 120,LocalDateTime.of(2020,3,10,0,0),"Author 15" ));
    }
}
