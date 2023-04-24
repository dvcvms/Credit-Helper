package ru.mts.credit_registration.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mts.credit_registration.model.*;
import ru.mts.credit_registration.serivce.impl.LoanOrderServiceImpl;
import ru.mts.credit_registration.serivce.impl.TariffServiceImpl;

@RestController
@RequestMapping("/loan-service")
@RequiredArgsConstructor
public class LoanOrderController {

    private final TariffServiceImpl tariffService;
    private final LoanOrderServiceImpl orderService;

    @GetMapping("/getTariffs")
    public ResponseEntity<DataResponse<DataTariffsResponse>> getTariffs() {
        return ResponseEntity.ok(tariffService.getTariffs());
    }

    @PostMapping("/order")
    public ResponseEntity<DataResponse<DataLoanOrderResponse>> createOrder(@RequestBody LoanApplicationRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/getStatusOrder")
    public ResponseEntity<DataResponse<DataStatusResponse>> getStatusOrder(@RequestParam String orderId) {
        return ResponseEntity.ok(orderService.getStatusByOrderId(orderId));
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<Void> deleteOrder(@RequestBody DeleteApplicationRequest request) {
        orderService.deleteOrder(request);
        return ResponseEntity.ok().build();
    }
}
