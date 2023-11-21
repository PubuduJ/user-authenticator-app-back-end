package lk.pubudu.app.dto;

import jakarta.validation.constraints.*;
import lk.pubudu.app.util.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5120540903909433091L;
    @Null(groups = ValidationGroups.Create.class, message = "Id should be null")
    @NotNull(groups = ValidationGroups.Update.class, message = "Id is required")
    private Long id;
    @NotNull(message = "Image url cannot have a null value")
    private String img;
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z][A-Za-z ]+$", message = "Invalid first name")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[A-Za-z][A-Za-z ]+$", message = "Invalid last name")
    private String lastName;
    @NotBlank(message = "Email ID is required")
    @Email(message = "Enter a valid email id")
    private String email;
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[+]?[0-9]+$", message = "Invalid mobile number format")
    private String mobile;
    @NotEmpty(message = "User role ids are required")
    private Integer[] roleIds;
    private Boolean fresh;
}
