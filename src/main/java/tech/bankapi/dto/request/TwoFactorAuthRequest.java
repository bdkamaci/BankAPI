package tech.bankapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TwoFactorAuthRequest {
    private Long userId;
    private String method;
    private String twoFactorCode;
}
