package tech.bankapi.dto.request;

import jakarta.validation.constraints.Email;
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
    private String password;

    @NonNull
    private String phone;
}
