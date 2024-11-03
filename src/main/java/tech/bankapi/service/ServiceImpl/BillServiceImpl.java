package tech.bankapi.service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bankapi.core.config.ModelMapper.ModelMapperService;
import tech.bankapi.dto.request.BillRequest;
import tech.bankapi.dto.response.BillResponse;
import tech.bankapi.model.Bill;
import tech.bankapi.repository.BillRepository;
import tech.bankapi.service.BillService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public BillResponse createBill(BillRequest billRequest) {
        if(billRepository.findById(modelMapperService.forRequest().map(billRequest, Bill.class).getId()).isEmpty()) {
            Bill bill = modelMapperService.forRequest().map(billRequest, Bill.class);
            bill = billRepository.save(bill);
            return modelMapperService.forResponse().map(bill, BillResponse.class);
        } else {
            throw new RuntimeException("Bill already exists");
        }
    }

    @Override
    public BillResponse updateBill(BillRequest billRequest) {
        Bill bill = billRepository.findById(modelMapperService.forRequest().map(billRequest, Bill.class).getId())
                .orElseThrow(() -> new RuntimeException("Bill not found!"));
        modelMapperService.forRequest().map(billRequest, bill);
        billRepository.saveAndFlush(bill);
        return modelMapperService.forResponse().map(bill, BillResponse.class);
    }

    @Override
    public void deleteBill(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));
        billRepository.delete(bill);
    }

    @Override
    public BillResponse getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));
        return modelMapperService.forResponse().map(bill, BillResponse.class);
    }

    @Override
    public List<BillResponse> getAccountBills(Long accountId) {
        List<Bill> bills = billRepository.findByAccountId(accountId);
        return bills.stream()
                .map(bill -> modelMapperService.forResponse().map(bill, BillResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BillResponse> getAllBills() {
        return billRepository.findAll().stream()
                .map(bill -> modelMapperService.forResponse().map(bill, BillResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public BillResponse payBill(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + billId));

        if (bill.getIsPaid()) {
            throw new RuntimeException("Bill already paid");
        }

        bill.setIsPaid(true); // Marking bill as paid
        billRepository.save(bill);

        return modelMapperService.forResponse().map(bill, BillResponse.class);
    }
}
