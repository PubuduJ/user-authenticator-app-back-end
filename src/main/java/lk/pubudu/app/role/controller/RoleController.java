package lk.pubudu.app.role.controller;

import lk.pubudu.app.dto.RoleDTO;
import lk.pubudu.app.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin
public class RoleController {

    private final RoleService roleService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleDTO));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<RoleDTO> updateRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.updateRole(roleDTO));
    }

    @GetMapping(params = "q", produces = "application/json")
    public ResponseEntity<List<RoleDTO>> getRolesByRoleName(@RequestParam String q) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRolesByRoleName(q));
    }
}
