package ru.mts.credit_registration.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mts.credit_registration.model.*;
import ru.mts.credit_registration.serivce.impl.LoanOrderServiceImpl;

@RestController
@RequestMapping("/loan-service")
@RequiredArgsConstructor
public class LoanOrderController {

    private final LoanOrderServiceImpl orderService;

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
