package com.dukaan.userservice.adapters.inbound;

import com.dukaan.userservice.application.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String roleName) {
        try {
            userService.registerUser(username, password, roleName);
            return ResponseEntity.ok("User Registered SuccessFully!");
        }
        catch (IllegalAccessException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/login")
    public String login() {
        return "Login successful!";
    }
}
