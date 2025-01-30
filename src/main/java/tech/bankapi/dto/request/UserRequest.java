package tech.bankapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {
    @NonNull
    private String name;

    @NonNull
    @Email
    private String email;

    @NonNull
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NonNull
    private String phone;
}
