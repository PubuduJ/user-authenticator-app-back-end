package lk.pubudu.app.user.controller;

import lk.pubudu.app.dto.UserDTO;
import lk.pubudu.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return null;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return null;
    }

    @PatchMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        return null;
    }

    @GetMapping(params = "q", produces = "application/json")
    public ResponseEntity<List<UserDTO>> getUsersByQuery(@RequestParam String q) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersByQuery(q));
    }

}
