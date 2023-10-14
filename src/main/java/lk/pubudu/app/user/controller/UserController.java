package lk.pubudu.app.user.controller;

import lk.pubudu.app.dto.UserDTO;
import lk.pubudu.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping(params = "q", produces = "application/json")
    public ResponseEntity<List<UserDTO>> getUsersByQuery(@RequestParam String q) {
        System.out.println("OK");
        return null;
    }

}
