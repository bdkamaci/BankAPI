package tech.bankapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SecurityLogResponse {
    private Long id;
    private LocalDateTime date;
    private String action;
    private Long userId;
}
