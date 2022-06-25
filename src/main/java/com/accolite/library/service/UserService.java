package com.accolite.library.service;

import com.accolite.library.model.Books;
import com.accolite.library.model.Role;
import com.accolite.library.model.User;
import com.accolite.library.repository.RoleRepository;
import com.accolite.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository urepo;

    @Autowired
    private RoleRepository rrepo;

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

    public void createAdmin(User user) throws Exception {
        if(user.getFullname() == null || user.getEmail() == null){
            throw new Exception();
        }
        Optional<Role> role = rrepo.findById(2);
        Role newRole = role.get();
        user.setRole(newRole);
        urepo.save(user);
    }


    public String changePassword(String fullname, String oldPassword, String newPassword){
        Optional<User> user = urepo.findByFullname(fullname);
        if(!user.isPresent()){
            return "User not found";
        }
        else if(!Objects.equals(user.get().getPassword(), oldPassword)){
            return "Invalid credentials";
        }

        User updated = user.get();
        updated.setPassword(newPassword);
        urepo.save(updated);
        return "Password updated";
    }

    public String deleteUser(User user){
        Role role = user.getRole();
        if(role.getRid() == 1){
            return "Invalid request";
        }
        Optional<Role> newRole = rrepo.findById(4);
        Role newRole1 = newRole.get();
        user.setRole(newRole1);
        urepo.save(user);
        return "User deleted successfully";
    }

}
