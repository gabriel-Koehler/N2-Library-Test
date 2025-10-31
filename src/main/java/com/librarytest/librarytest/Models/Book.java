package com.librarytest.librarytest.Models;

import java.time.LocalDateTime;

public class Book {
    private Long id_book;
    private String title;
    private String synopsis;
    private Integer numberOfPages;
    private LocalDateTime dateThrow;
    private String author;

    public Book(Long id_book, String title, String synopsis, Integer numberOfPages, LocalDateTime dateThrow, String author) {
        this.id_book = id_book;
        this.title = title;
        this.synopsis = synopsis;
        this.numberOfPages = numberOfPages;
        this.dateThrow = dateThrow;
        this.author = author;

    }

    public Long getId_book() {
        return id_book;
    }

    public void setId_book(Long id_book) {
        this.id_book = id_book;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public LocalDateTime getDateThrow() {
        return dateThrow;
    }

    public void setDateThrow(LocalDateTime dateThrow) {
        this.dateThrow = dateThrow;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    
}
