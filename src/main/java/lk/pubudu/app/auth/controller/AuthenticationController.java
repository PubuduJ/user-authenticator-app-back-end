package lk.pubudu.app.auth.controller;

import lk.pubudu.app.auth.service.AuthenticationService;
import lk.pubudu.app.dto.AuthenticationRequestDTO;
import lk.pubudu.app.dto.AuthenticationResponseDTO;
import lk.pubudu.app.dto.PasswordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin()
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/authenticate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping(path = "/forgot/password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.forgotPassword(email));
    }

    @PostMapping(path = "/update/password", consumes = "application/json")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordDTO passwordDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.updatePassword(passwordDTO));
    }

}
