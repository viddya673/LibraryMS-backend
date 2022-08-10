package com.accolite.library.service;

import com.accolite.library.model.Books;
import com.accolite.library.model.Issue;
import com.accolite.library.model.Role;
import com.accolite.library.model.User;
import com.accolite.library.repository.BooksRepository;
import com.accolite.library.repository.IssueRepository;
import com.accolite.library.repository.RoleRepository;
import com.accolite.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository urepo;

    @Autowired
    private RoleRepository rrepo;

    @Autowired
    private BooksRepository brepo;

    @Autowired
    private IssueRepository irepo;

    public List<User> getAllUsers(){
        Optional<Role> orole = rrepo.findById(3);
        Role role = orole.get();
        return urepo.findByRole(role);
    }

    public void createUser(User user) throws Exception {
        if(user.getFullname() == null || user.getEmail() == null){
            throw new Exception();
        }
        Optional<Role> role = rrepo.findById(3);
        Role newRole = role.get();
        user.setRole(newRole);
        urepo.save(user);
    }

    public User changePassword(int uid, String oldPassword, String newPassword){
        Optional<User> user = urepo.findById(uid);
        if(!user.isPresent()){
            return null;
        }
        else if(!Objects.equals(user.get().getPassword(), oldPassword)){
            return null;
        }

        User updated = user.get();
        updated.setPassword(newPassword);
        urepo.save(updated);
        return updated;
    }

    public User changePasswordByAdmin(int uid, String newPassword){
        User user = urepo.findById(uid).get();
        user.setPassword(newPassword);
        urepo.save(user);
        return user;
    }

    public User userLogin(String email, String pass){
        Optional<User> user = urepo.findByEmail(email);
        User data = user.get();
        if(data.getPassword().equals(pass)){
            return data;
        }
        return null;
    }

    public User deleteUser(User user){
        Role role = user.getRole();
        if(role.getRid() == 1){
            return null;
        }
        Optional<Role> newRole = rrepo.findById(4);
        Role newRole1 = newRole.get();
        user.setRole(newRole1);
        urepo.save(user);
        return user;
    }

    public List<Books> getAllBooks() {
        return brepo.findAll();
    }

    public List<Books> getAvailableBooks() {
        List<Books> allBooks = brepo.findAll();
        List<Books> availBooks = new ArrayList<Books>();
        for(Books book: allBooks){
            if(book.getNoOfCopies() != 0){
                availBooks.add(book);
            }
        }
        return availBooks;
    }

    public void addBook(Books book){
        brepo.save(book);
    }

    public void removeBook(int bid) {
        Optional<Books> book = brepo.findById(bid);
        Books book1 = book.get();
        brepo.delete(book1);
    }

    public Issue issueBook(int uid, int bid){

        Issue issue = new Issue();

        User user = urepo.findById(uid).get();

        Books book = brepo.findById(bid).get();
        if(book.getNoOfCopies() == 0){
            return null;
        }

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = LocalDate.parse(issueDate.toString()).plusDays(7);

        book.setNoOfCopies(book.getNoOfCopies()-1);

        Random rand = new Random();
        issue.setIid(rand.nextInt(2000));

        issue.setUser(user);
        issue.setBooks(book);
        issue.setIssueDate(issueDate);
        issue.setDueDate(dueDate);
        issue.setReturnDate(null);

        Issue response = irepo.save(issue);
        return response;
    }
    public User usersByIssue(int bid){
        Optional<Issue> issue = irepo.findByBooks(brepo.findById(bid).get());
        User user = issue.get().getUser();
        return user;
    }

    public List<User> overdueUsers(){
        List<User> overdueUsers = new ArrayList<>();
        List<Issue> issues = irepo.findAll();
        for(Issue issue: issues){
            if(LocalDate.now().isAfter(issue.getDueDate())){
                overdueUsers.add(issue.getUser());
            }
        }
        System.out.println(overdueUsers);
        return overdueUsers;
    }

    public Issue returnBook(int iid){
        Optional<Issue> data =irepo.findById(iid);
        if(data.get().getReturnDate() != null){
            System.out.println(data.get().getReturnDate());
            return null;
        }
        LocalDate returnDate = LocalDate.now();
        data.get().setReturnDate(returnDate);
        Optional<Books> bookToBeReturned = brepo.findById(data.get().getBooks().getBid());
        bookToBeReturned.get().setNoOfCopies(bookToBeReturned.get().getNoOfCopies()+1);

        brepo.save(bookToBeReturned.get());
        irepo.save(data.get());
        return data.get();
    }

    public List<Issue> getBooksBorrowed(int uid){

        User user = urepo.findById(uid).get();
        return irepo.findAllByUser(user);
    }

    public List<Issue> overdueByUser(int uid){
        List<Issue> overdues = new ArrayList<>();
        User user = urepo.findById(uid).get();
        List<Issue> issues = irepo.findAllByUser(user);
        for(Issue issue: issues){
            if(LocalDate.now().isAfter(issue.getDueDate()) && (issue.getReturnDate() == null)){
                overdues.add(issue);
            }
        }
        return overdues;
    }

    public List<User> getUserByBook(int bid){
        List<Issue> issues = irepo.findAll();
        Books book = brepo.findById(bid).get();
        List<User> users = new ArrayList<>();
        for(Issue issue: issues) {
            if (issue.getBooks() == book && (issue.getReturnDate() == null)) {
                users.add(issue.getUser());
            }
        }
        return users;
    }
}
