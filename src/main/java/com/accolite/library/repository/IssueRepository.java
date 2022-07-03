package com.accolite.library.repository;

import com.accolite.library.model.Books;
import com.accolite.library.model.Issue;
import com.accolite.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    Optional<Issue> findByBooks(Books book);
    List<Issue> findAllByUser(User user);
}
