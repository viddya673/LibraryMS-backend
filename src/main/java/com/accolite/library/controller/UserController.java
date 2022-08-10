package com.accolite.library.controller;

import com.accolite.library.model.*;
import com.accolite.library.repository.UserRepository;
import com.accolite.library.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.print.attribute.standard.PresentationDirection;
import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService uservice;

    @Autowired
    private UserRepository urepo;

    @GetMapping(value = "users")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<List<User>>(uservice.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping(value = "users/{uid}")
    public ResponseEntity<User> getUserById(@PathVariable("uid") int uid){
        User user = urepo.findById(uid).get();
        System.out.println(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping(value = "create/user")
    public String createUser(@RequestBody User user) throws Exception {
        if(user.getFullname().isEmpty() || user.getPassword().isEmpty()){
            return "User not created";
        }
        uservice.createUser(user);
        return "User created successfully, User Id generated=" + user.getUid();
    }

    @GetMapping(value = "user/{email}")
    public ResponseEntity<User> userByEmail(@PathVariable("email") String email){
        User user = urepo.findByEmail(email).get();
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping(value = "user/login")
    public ResponseEntity<User> authenticateUser(@RequestBody UserLogin login) {
        User response = uservice.userLogin(login.email, login.password);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<User>(response, HttpStatus.OK);
    }

    @PutMapping(value = "admin-update/password/{uid}")
    public ResponseEntity<User> updatePaswordbyAdmin(@PathVariable("uid") int uid, @RequestBody User user){
        User updated = uservice.changePasswordByAdmin(uid, user.getPassword());
        return new ResponseEntity<User>(updated, HttpStatus.OK);
    }

    @PutMapping(value = "update/password/{uid}")
    //update/password//?email=viddya@gmail.com&name=Viddya
    public ResponseEntity<User> changePassword(@PathVariable("uid") int uid, @RequestBody changePassword change){
        User user = uservice.changePassword(uid, change.oldPassword, change.newPassword);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping(value = "delete/user/{uid}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable("uid") int uid){
        Optional<User> user = urepo.findById(uid);
        User user1 = user.get();
        User response = uservice.deleteUser(user1);
        Map<String, Boolean> output = new HashMap<>();
        if(response != null) {
            output.put("Deleted", Boolean.TRUE);
            return new ResponseEntity<Map<String, Boolean>>(output, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "books")
    public ResponseEntity<List<Books>> getBooks(){
        return new ResponseEntity<List<Books>>(uservice.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping(value = "books/available")
    public ResponseEntity<List<Books>> getAvailableBooks(){
        return new ResponseEntity<List<Books>>(uservice.getAvailableBooks(), HttpStatus.OK);
    }

    @PostMapping(value = "add/book")
    public void addBook(@RequestBody Books book){
        uservice.addBook(book);
    }

    @DeleteMapping(value = "remove/book/{bid}")
    public void removeBook(@PathVariable("bid") int bid){
        uservice.removeBook(bid);
    }

    @PostMapping(value = "issue/book/{uid}/{bid}")
    public Issue issueBook(@PathVariable("uid") int uid, @PathVariable("bid") int bid){
       Issue response = uservice.issueBook(uid, bid);
       System.out.println(response);
       return response;
    }

    @GetMapping(value = "overdues")
    public ResponseEntity<List<User>> overdueUsers(){
        List<User> overdueUsers = uservice.overdueUsers();
        return new ResponseEntity<List<User>>(overdueUsers, HttpStatus.OK);
    }

    @PutMapping(value = "book/return/{iid}")
    public ResponseEntity<Issue> returnBook(@PathVariable("iid") int iid){
        Issue response = uservice.returnBook(iid);
        return new ResponseEntity<Issue>(response, HttpStatus.OK);
    }

    @GetMapping("/issues/user/{uid}")
    public ResponseEntity<List<Issue>> findIssuesById(@PathVariable("uid") int uid){
        List<Issue> response = uservice.getBooksBorrowed(uid);
        return new ResponseEntity<List<Issue>>(response, HttpStatus.OK);
    }

    @GetMapping("/overdues/user/{uid}")
    public ResponseEntity<List<Issue>> overduesById(@PathVariable("uid") int uid){
        List<Issue> response = uservice.overdueByUser(uid);
        return new ResponseEntity<List<Issue>>(response, HttpStatus.OK);
    }

    @GetMapping("/users-by-book/{bid}")
    public ResponseEntity<List<User>> getUsersByBook(@PathVariable("bid") int bid){
//        List<User> users = ;
        return new ResponseEntity<List<User>>(uservice.getUserByBook(bid), HttpStatus.OK);
    }
}
