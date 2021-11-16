package com.ironhack.playlistservice.controller;

import com.ironhack.playlistservice.dto.UpdateRequest;
import com.ironhack.playlistservice.dto.UserDTO;
import com.ironhack.playlistservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins= {"http://localhost:4200", "http://localhost:8000"}, allowedHeaders = "*")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return "no auth get works";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getByUsername(@PathVariable("email") String email) throws ParseException {
        return userService.getByEmail(email);
    }


    @PatchMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable("email") String email, @RequestBody UpdateRequest updateRequest){
        userService.updateUser(email, updateRequest);
    }

    @PostMapping("/{email}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDTO userDTO) throws ParseException {
        userService.createUser(userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
