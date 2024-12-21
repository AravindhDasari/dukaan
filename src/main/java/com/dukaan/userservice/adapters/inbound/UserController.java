package com.dukaan.userservice.adapters.inbound;

import com.dukaan.userservice.application.service.UserService;
import com.dukaan.userservice.domain.model.dto.AssignRoleDTO;
import com.dukaan.userservice.domain.model.dto.Message;
import com.dukaan.userservice.domain.model.dto.UserProfileDTO;
import org.springframework.data.domain.Page;
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

    @GetMapping("/users")
    public ResponseEntity<Page<UserProfileDTO>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size,
                                                            @RequestParam(defaultValue = "userId") String sortByParameter,
                                                            @RequestParam(defaultValue = "true") boolean ascending) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortByParameter, ascending));
    }

    @DeleteMapping("/user")
    public ResponseEntity<Message> deleteUser(@RequestParam(required = false) Long id,
                                              @RequestParam(required = false) String username) {
        if (id != null) {
            return ResponseEntity.ok(userService.deleteUserById(id));
        } else if (username != null) {
            return ResponseEntity.ok(userService.deleteUserByUserName(username));
        } else {
            String message = "";
            if (id == null)
                message += "id is Null";
            if (username == null)
                message += "username is Null";
            return ResponseEntity.ok(new Message(message));
        }

    }

    @GetMapping("/profile/get")
    public ResponseEntity<?> getProfile(@RequestParam String userName) {
        try {
            return ResponseEntity.ok(userService.getProfileDetails(userName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Message(e.getMessage()));
        }
    }

    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfile( @RequestBody UserProfileDTO userProfileDTO) {
        try {
            return ResponseEntity.ok(userService.updateProfileDetails(userProfileDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message(e.getMessage()));
        }
    }

    @PutMapping("/user/assignRole")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleDTO assignRoleDTO) {
        return ResponseEntity.ok(userService.assignRole(assignRoleDTO));
    }

}
