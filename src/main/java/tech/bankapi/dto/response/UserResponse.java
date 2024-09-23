package tech.bankapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;

    private List<AccountResponse> accounts;
    private List<SecurityLogResponse> securityLogs;
}
