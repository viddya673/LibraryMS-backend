package com.accolite.library.controller;

import com.accolite.library.model.User;
import com.accolite.library.repository.UserRepository;
import com.accolite.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping(value = "create/user")
    public ResponseEntity<String> createUser(@RequestBody User user) throws Exception {
        if(user.getFullname().isEmpty() || user.getPassword().isEmpty()){
            return new ResponseEntity<String>("User not created", HttpStatus.BAD_REQUEST);
        }
        uservice.createUser(user);
        return new ResponseEntity<String>("User created successfully, User Id generated=" + user.getUid() , HttpStatus.CREATED);
    }

    @PutMapping(value = "update/password/{fullname}/{oldPassword}")
    //update/password//?email=viddya@gmail.com&name=Viddya
    public ResponseEntity<String> changePassword(@PathVariable("fullname") String fullname, @PathVariable("oldPassword") String oldPassword, @RequestParam String newPassword){
        String response = uservice.changePassword(fullname, oldPassword, newPassword);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/user/{uid}")
    public ResponseEntity<String> deleteUser(@PathVariable("uid") int uid){
        Optional<User> user = urepo.findById(uid);
        User user1 = user.get();
        String response = uservice.deleteUser(user1);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

}
