package com.accolite.library.repository;

import com.accolite.library.model.Role;
import com.accolite.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByFullname(String fullname);

    List<User> findByRole(Role role);

    Optional<User> findByEmail(String email);

    Optional<User> findByUid(int uid);

}
