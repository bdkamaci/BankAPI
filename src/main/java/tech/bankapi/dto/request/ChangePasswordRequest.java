package tech.bankapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangePasswordRequest {
    private Long userId;
    private String currentPassword;
    private String newPassword;
}
