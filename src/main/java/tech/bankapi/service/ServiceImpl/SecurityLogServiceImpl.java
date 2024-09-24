package tech.bankapi.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bankapi.core.config.ModelMapper.ModelMapperService;
import tech.bankapi.dto.response.SecurityLogResponse;
import tech.bankapi.entity.SecurityLog;
import tech.bankapi.entity.User;
import tech.bankapi.repository.SecurityLogRepository;
import tech.bankapi.repository.UserRepository;
import tech.bankapi.service.SecurityLogService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SecurityLogServiceImpl implements SecurityLogService {
    private final SecurityLogRepository securityLogRepository;
    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public List<SecurityLogResponse> getUserLogs(Long userId) {
        List<SecurityLog> logs = securityLogRepository.findByUserId(userId);
        return logs.stream()
                .map(log -> modelMapperService.forResponse().map(log, SecurityLogResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SecurityLogResponse> getAllLogs() {
        return securityLogRepository.findAll().stream()
                .map(logs -> modelMapperService.forResponse().map(logs, SecurityLogResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void logAction(Long userId, String action) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SecurityLog log = new SecurityLog();
        log.setUser(user);
        log.setAction(action);
        log.setDate(LocalDateTime.now());

        securityLogRepository.save(log);
    }
}
