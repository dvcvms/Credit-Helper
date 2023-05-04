package ru.mts.credit_registration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mts.credit_registration.model.DataResponse;
import ru.mts.credit_registration.model.DataTariffsResponse;
import ru.mts.credit_registration.serivce.impl.TariffServiceImpl;

@Controller
@RequestMapping("/loan-service")
@RequiredArgsConstructor
public class TariffController {

    private final TariffServiceImpl tariffService;

    @GetMapping("/getTariffs")
    public ResponseEntity<DataResponse<DataTariffsResponse>> getTariffs() {
        return ResponseEntity.ok(tariffService.getTariffs());
    }
}
