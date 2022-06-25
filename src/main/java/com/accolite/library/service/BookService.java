package com.accolite.library.service;

import com.accolite.library.model.Books;
import com.accolite.library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BooksRepository brepo;

    public void addBooks(Books books) {
        brepo.save(books);
    }

    public void removeBooks(Books books){
        brepo.delete(books);
    }
}
