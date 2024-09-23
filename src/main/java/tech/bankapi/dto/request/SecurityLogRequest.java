package tech.bankapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SecurityLogRequest {
    @NonNull
    private String action;

    @NonNull
    private Long userId;
}
