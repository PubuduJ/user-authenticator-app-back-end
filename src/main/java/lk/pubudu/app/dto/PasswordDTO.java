package lk.pubudu.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1246788011909087277L;
    @NotBlank(message = "Email ID is required")
    @Email(message = "Enter a valid email id")
    private String email;
    @NotBlank(message = "Temporary password is required")
    private String temporaryPassword;
    @NotBlank(message = "New Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Invalid password")
    private String newPassword;
}
