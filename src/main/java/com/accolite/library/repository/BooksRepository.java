package com.accolite.library.repository;

import com.accolite.library.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {

    Optional<Books> findByBid(int bid);
}
