package tech.bankapi.service;

import tech.bankapi.dto.request.BillRequest;
import tech.bankapi.dto.response.BillResponse;

import java.util.List;

public interface BillService {
    BillResponse createBill(BillRequest billRequest);
    BillResponse updateBill(BillRequest billRequest);
    void deleteBill(Long id);
    BillResponse getBillById(Long id);
    List<BillResponse> getAccountBills(Long accountId);
    List<BillResponse> getAllBills();

    BillResponse payBill(Long billId);
}
