package lk.pubudu.app.role.controller;

import lk.pubudu.app.role.entity.Role;
import lk.pubudu.app.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin
public class RoleController {

    private final RoleService roleService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        roleService.createRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

}
