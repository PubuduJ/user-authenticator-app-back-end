package lk.pubudu.app.user.controller;

import lk.pubudu.app.dto.UserDTO;
import lk.pubudu.app.user.service.UserService;
import lk.pubudu.app.util.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> createUser(@Validated(ValidationGroups.Create.class) @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> updateUser(@Validated(ValidationGroups.Update.class) @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(userDTO));
    }

    @GetMapping(params = "q", produces = "application/json")
    public ResponseEntity<List<UserDTO>> getUsersByQuery(@RequestParam String q) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersByQuery(q));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "/reset/password/{id}")
    public ResponseEntity<?> resetPasswordByUserId(@PathVariable Long id) {
        userService.resetPassword(id);
        return ResponseEntity.noContent().build();
    }

}
