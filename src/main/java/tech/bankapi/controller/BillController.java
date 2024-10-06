package tech.bankapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.bankapi.dto.response.BillResponse;
import tech.bankapi.service.BillService;

import java.util.List;

@Controller
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping
    public ResponseEntity<List<BillResponse>> getAccountBills(@RequestParam Long accountId) {
        List<BillResponse> bills = billService.getAccountBills(accountId);
        return ResponseEntity.ok(bills);
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<BillResponse> payBill(@PathVariable Long id) {
        BillResponse billResponse = billService.payBill(id);
        return ResponseEntity.ok(billResponse);
    }
}
