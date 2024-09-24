package tech.bankapi.service;

import tech.bankapi.dto.response.SecurityLogResponse;

import java.util.List;

public interface SecurityLogService {
    List<SecurityLogResponse> getUserLogs(Long userId);
    List<SecurityLogResponse> getAllLogs();
    void logAction(Long userId, String action);
}
